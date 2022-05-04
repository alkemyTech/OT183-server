package com.alkemy.ong.seed;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component


public class ActivitySeeder implements CommandLineRunner  {


    @Autowired
    ActivityRepository repository;

    private final String[] name ={
            "apoyo escolar nivel primario",
            "apoyo escolar nivel secundaria",
            "tutorias"

    };

    private final String[] content = {
            "El espacio de apoyo escolar es el corazon del area educativa. " +
                    "Se realizan los talleres de lunes a jueves de 10 a 12 horas y de 14 a 16 horas en el contraturno.",

            "Del mismo modo que en primaria, este taller es el corazon del area secundaria. " +
                    "Se ralizan talleres de lunes a viernes de 10 a 12 horas y de 16 a 18 horas en el contraturno.",

            "Es un programa destinado a jovenes a partir del tercer año de secundaria, cuyo objetivo es garantizar su " +
                    "permanencia en la escula y constuir un proyecto de vida que da sentida al colegio."
    };

    private final String[] image = {
            "image1.jpg",
            "image2.jpg",
            "image3.jpg"
    };

    @Override
    public void run(String... args) throws Exception {

        if(repository.getActivityQuantity() > 0){return;}

        if(repository.getActivityQuantity() == 0){
            loadActivity();
        }

    }

    private void loadActivity(){

        for(int i=0;i<3;i++){
            createActivity(name[i],content[i],image[i]);
        }
    }

    private void createActivity(String name,String content,String image){
        Activity activity = Activity.builder()
                .name(name)
                .content(content)
                .image(image)
                .build();
        repository.save(activity);
    }
}
