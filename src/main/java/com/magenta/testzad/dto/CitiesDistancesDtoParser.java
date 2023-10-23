package com.magenta.testzad.dto;


import com.magenta.testzad.entity.City;
import com.magenta.testzad.entity.Distance;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для чтения/записи списков новых городов и дистанций в/из xml - файла. И последующей сериалицации/дисериализации для записи в БД. <br>
 * При написании xml - файла нужно учитывать несколько факторов: <br>
 * 1) Сначала должен идти список добавляемых городов, потом дистанции<br>
 * 2) В БД используется авто-генерация первичных ключей, а это значит, что при написании списка дистанций нужно вписывать idFromCity и idToCity,
 * тех городов, которые уже есть в БД или те id городов, которые были добавлены непостредственно из данного файла. <br>
 * Пример xml - файла: <br>
 * &lt;xml version="1.0" encoding="UTF-8" standalone="yes"?&gt; <br>
 * &lt;CitiesDistance&gt; <br>
 * <pre>    &lt;city id="6"&gt;</pre>
 * <pre>        &lt;name&gt;Saratov&lt;/name&gt;</pre>
 * <pre>        &lt;latitude&gt;871&lt;/latitude&gt;</pre>
 * <pre>        &lt;longitude&gt;5475&lt;/longitude&gt;</pre>
 * <pre>    &lt;/city&gt;</pre>
 * <pre>    &lt;city id="7"&gt;</pre>
 * <pre>        &lt;name&gt;Vladivostok&lt;/name&gt;</pre>
 * <pre>        &lt;latitude&gt;87141&lt;/latitude&gt;</pre>
 * <pre>        &lt;longitude&gt;547&lt;/longitude&gt;</pre>
 * <pre>    &lt;/city&gt;</pre>
 * <pre>    &lt;distance id="8"&gt;</pre>
 * <pre>        &lt;distance&gt;35754.01&lt;/distance&gt;</pre>
 * <pre>        &lt;idFromCity&gt;6&lt;/idFromCity&gt;</pre>
 * <pre>        &lt;idToCity&gt;2&lt;/idToCity&gt;</pre>
 * <pre>    &lt;/distance&gt;</pre>
 * <pre>&lt;/CitiesDistance&gt;</pre>
 */

@Getter
@Component
@XmlRootElement(name = "CitiesDistance")
@Slf4j
public class CitiesDistancesDtoParser {

    private ArrayList<CityDto> cities;

    private ArrayList<DistanceDto> distances;


    public CitiesDistancesDtoParser() {
        cities = new ArrayList<>();
        distances = new ArrayList<>();
    }

    @XmlElement(name = "city")
    public ArrayList<CityDto> getCities() {
        return cities;
    }

    @XmlElement(name = "distance")
    public ArrayList<DistanceDto> getDistances() {
        return distances;
    }

    public void addCities(CityDto cityDTO) {
        cities.add(cityDTO);
    }

    public void addDistance(DistanceDto distanceDTO) {
        distances.add(distanceDTO);
    }


    public void marshal(List<CityDto> cityDTOList, List<DistanceDto> distanceDTOList, String path) {
        CitiesDistancesDtoParser citiesDistancesDTO = new CitiesDistancesDtoParser();

        if (path.isEmpty()) {
            path = "src/main/resources/CitiesDistances.xml";
        }

        for (CityDto cityDTO : cityDTOList) {
            citiesDistancesDTO.addCities(cityDTO);
        }
        for (DistanceDto distanceDTO : distanceDTOList) {
            citiesDistancesDTO.addDistance(distanceDTO);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(CitiesDistancesDtoParser.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(citiesDistancesDTO, new File(path));
        } catch (JAXBException e) {
            log.warn("Error marshalling CitiesDistancesDtoParser: {}", e.getMessage(), e);
        }
    }

    public void marshalCityListAndDistanceList(List<City> cityList, List<Distance> distanceList, String path) {
        List<CityDto> cityDTOList = cityList.stream()
                .map(city -> CityDto.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .latitude(city.getLatitude())
                        .longitude(city.getLongitude())
                        .build())
                .collect(Collectors.toList());

        List<DistanceDto> distanceDTOList = distanceList.stream()
                .map(distance -> DistanceDto.builder()
                        .id(distance.getId())
                        .distance(distance.getDistance())
                        .idFromCity(distance.getIdFromCity())
                        .idToCity(distance.getIdToCity())
                        .build())
                .collect(Collectors.toList());

        marshal(cityDTOList, distanceDTOList, path);
    }

    public void unmarshal(String path) {
        CitiesDistancesDtoParser citiesDistancesDTO = new CitiesDistancesDtoParser();

        if (!distances.isEmpty()) {
            distances.clear();
        }
        if (!cities.isEmpty()) {
            cities.clear();
        }

        try {
            JAXBContext context = JAXBContext.newInstance(CitiesDistancesDtoParser.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            citiesDistancesDTO = (CitiesDistancesDtoParser) unmarshaller.unmarshal(new FileReader(path));

            for (CityDto cityDTO : citiesDistancesDTO.getCities()) {
                CityDto tempCityDTO = (CityDto) cityDTO.clone();
                cities.add(tempCityDTO);
            }
            for (DistanceDto distanceDTO : citiesDistancesDTO.getDistances()) {
                DistanceDto tempDistanceDTO = (DistanceDto) distanceDTO.clone();
                distances.add(tempDistanceDTO);
            }
        } catch (JAXBException e) {
            log.warn("Error marshalling CitiesDistancesDtoParser: {}", e.getMessage(), e);
        } catch (CloneNotSupportedException e1) {
            log.warn("Error cloning object: {}", e1.getMessage(), e1);
        } catch (FileNotFoundException e2) {
            log.warn("File not found: {}", e2.getMessage(), e2);
        }
    }

    public List<City> cityDTOListToCityList() {
        return this.cities.stream()
                .map(CityDto::toCity)
                .collect(Collectors.toList());
    }

    public List<Distance> distanceDTOListToDistanceList() {
        return this.distances.stream()
                .map(DistanceDto::toDistance)
                .collect(Collectors.toList());
    }

}
