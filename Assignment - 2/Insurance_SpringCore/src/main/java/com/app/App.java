package com.app;

import com.app.config.AppConfig;
import com.app.dao.PolicyDao;
import com.app.dao_impl.PolicyDaoImpl;
import com.app.enums.StatusType;
import com.app.model.Policy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Scanner sc = new Scanner(System.in);

        PolicyDao policyDao = context.getBean(PolicyDaoImpl.class);
        while(true) {
            System.out.println("1. Add Policy");
            System.out.println("2. Delete Policy by Id");
            System.out.println("3. Update Policy");
            System.out.println("4. All Policies ");
            System.out.println("5. Get Policies by id");
            System.out.println("0. Exit");
            int op = sc.nextInt();
            if (op == 0)
                break;
            switch (op) {
                case 1:
                    try {
                        Policy policy = readPolicy(sc);
                        int rows = policyDao.addPolicy(policy);
                        System.out.println(rows + " policy added");
                    } catch (Exception e) {
                        System.out.println("Policy not added: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter policy id: ");
                        int policyId = sc.nextInt();
                        int rows = policyDao.deletePolicyById(policyId);
                        System.out.println(rows + " policy deleted");
                    } catch (Exception e) {
                        System.out.println("Policy not deleted: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter policy id to update: ");
                        int policyId = sc.nextInt();
                        Policy policy = readPolicy(sc);
                        policy.setPolicyId(policyId);
                        int rows = policyDao.updatePolicy(policy);
                        System.out.println(rows + " policy updated");
                    } catch (Exception e) {
                        System.out.println("Policy not updated: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        policyDao.getAllPolicies().forEach(System.out::println);
                    } catch (Exception e) {
                        System.out.println("Could not fetch policies: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        System.out.print("Enter policy id: ");
                        int policyId = sc.nextInt();
                        Policy policy = policyDao.getPolicyById(policyId);
                        System.out.println(policy);
                    } catch (EmptyResultDataAccessException e) {
                        System.out.println("Policy not found");
                    } catch (Exception e) {
                        System.out.println("Could not fetch policy: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }

        sc.close();
        context.close();
    }

    private static Policy readPolicy(Scanner sc) {
        sc.nextLine();

        System.out.print("Enter policy number: ");
        String policyNumber = sc.nextLine();

        System.out.println("Status: PROPOSAL_SUBMITTED, QUOTE_GENERATED, ACTIVE, EXPIRED");
        System.out.print("Enter status: ");
        StatusType statusType = StatusType.valueOf(sc.nextLine().trim().toUpperCase());

        System.out.print("Enter start date (yyyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(sc.nextLine().trim());

        System.out.print("Enter end date (yyyy-MM-dd): ");
        LocalDate endDate = LocalDate.parse(sc.nextLine().trim());
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        System.out.print("Enter premium amount: ");
        float premiumAmount = sc.nextFloat();

        return new Policy(policyNumber, statusType, Instant.now(), startDate, endDate, premiumAmount);
    }
}
