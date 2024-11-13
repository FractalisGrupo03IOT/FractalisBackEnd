package com.fractalis.greentoolswebservice.cropStatus.application.internal.commandServices;

import com.fractalis.greentoolswebservice.cropStatus.application.internal.commandEntities.CropCloudRequest;
import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantCommandService;
import com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories.PlantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class PlantCommandServiceImpl implements PlantCommandService {
    private final PlantRepository plantRepository;

    @Autowired
    public PlantCommandServiceImpl(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }
    @Override
    public Plant createPlant(Long stationId, String name, String plantImage) {
        String url = "https://fractalies-edge-server-production.up.railway.app/api/v1/crop/links";
        RestTemplate restTemplate = new RestTemplate();

        Plant plant = new Plant(stationId, name, plantImage);
        Plant savedPlant = plantRepository.save(plant);

        CropCloudRequest request = new CropCloudRequest(savedPlant.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Crear HttpEntity con el cuerpo y encabezados
        HttpEntity<CropCloudRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error en la solicitud POST al API externo: " + e.getMessage(), e);
        }

        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            // La petición fue exitosa, ahora se guarda la entidad en la base de datos
            return savedPlant;
        } else {
            // Manejo de errores si la petición no fue exitosa
            plantRepository.deleteById(savedPlant.getId());
            throw new RuntimeException("La solicitud POST falló con código: " + response.getStatusCode());
        }
    }

    @Override
    public void deletePlant(Long plantId) {
        plantRepository.deleteById(plantId);
    }
}
