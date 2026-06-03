package com.app;

import com.app.config.AppConfig;
import com.app.dao.IncidentDao;
import com.app.dao_impl.IncidentDaoImpl;
import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;
import com.app.model.Incident;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        //System.out.println(context.getBean(DataSource.class));
        Scanner sc = new Scanner(System.in);
        IncidentDao incidentDao = context.getBean(IncidentDaoImpl.class);


        while(true) {
            System.out.println("1. Add Incident");
            System.out.println("2. Delete Incident by Id");
            System.out.println("3. Update Incident");
            System.out.println("4. All incidents ");
            System.out.println("5. Get Incident by id");
            System.out.println("0. Exit");
            int op = sc.nextInt();
            if (op == 0)
                break;
            switch (op) {
                case 1:
                    incidentDao.insert(new Incident(IncidentType.ABUSE, "incident details", IncidentStatus.ACTIVE));
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Enter the ID to update: ");
                    //int id = sc.nextInt();
                    try {
                        // verified the id
                        Incident incident = incidentDao.getById(sc.nextInt());
                        System.out.println("Existing incident record ");
                        System.out.println(incident);
                        // take new progress input

                        System.out.println("Enter Progress Details to edit");
                        sc.nextLine();
                        String progress = sc.nextLine();
                        // attach new progress details to existing incident
                        incident.setProgressDetails(progress);
                        // update the incident in DB
                        incidentDao.update(incident);
                    }catch(EmptyResultDataAccessException e){
                        System.out.println("invalid id");
                    }
                    break;
                case 4:
                    incidentDao.getAll().forEach(System.out::println);
                    break;
                case 5:
                    break;
            }
        }
        sc.close();
        context.close();
    }
}
