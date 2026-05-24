package com.controller;

import com.Exception.RecordNotFoundException;
import com.config.HibernateConfig;
import com.enums.StatusType;
import com.enums.VehicleType;
import com.model.Policy;
import com.model.User;
import com.service.AuthService;
import com.service.CustomerService;
import com.service.PolicyService;
import com.service.ReviewService;
import com.service.VehicleService;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Session session =  HibernateConfig.getSessionFactory().openSession();
        Scanner sc = new Scanner(System.in);
        PolicyService policyService = new PolicyService(session);
        AuthService authService = new AuthService(session);
        CustomerService customerService = new CustomerService(session);
        VehicleService vehicleService = new VehicleService(session);
        ReviewService reviewService = new ReviewService(session);

        System.out.println("----- AutoMobileInsuranceSystem: LOGIN ----- ");
        System.out.println("Enter the User Name: ");
        String userName = sc.nextLine();
        System.out.println("Enter the Password: ");
        String password = sc.nextLine();
        try {
            User user = authService.login(userName, password);
            switch(user.getRole().toString()) {
                case "CUSTOMER":
                    System.out.println("Customer Menu");
                    while(true) {
                        System.out.println("1. Add Policy");
                        System.out.println("2. Delete Policy by Id");
                        System.out.println("3. Fetch Policy");
                        System.out.println("4. Update Personal Details");
                        System.out.println("5. Add vehicle");
                        System.out.println("0. Exit");
                        int op = Integer.parseInt(sc.nextLine());
                        if(op ==0)
                            break;
                        switch(op) {
                            case 1:
                                Policy policy = new Policy();

                                System.out.print("Enter Policy Number (e.g. POL-2025-001): ");
                                policy.setPolicyNumber(sc.nextLine());

                                System.out.print("Enter Status (PROPOSAL_SUBMITTED / QUOTE_GENERATED / ACTIVE / EXPIRED): ");
                                policy.setStatusType(StatusType.valueOf(sc.nextLine().toUpperCase()));

                                System.out.print("Enter Start Date (yyyy-MM-dd): ");
                                policy.setStartDate(LocalDate.parse(sc.nextLine()));

                                System.out.print("Enter End Date (yyyy-MM-dd): ");
                                policy.setEndDate(LocalDate.parse(sc.nextLine()));

                                System.out.print("Enter Premium Amount: ");
                                policy.setPremiumAmount(Float.parseFloat(sc.nextLine()));

                                policyService.insert(policy);
                                System.out.println("Policy Added Successfully!");
                                break;
                            case 2:
                                System.out.println("Enter ID that to Deleted");
                                int id = Integer.parseInt(sc.nextLine());
                                try {
                                    policyService.deleteById(id);
                                    System.out.println("Policy Deleted Successfully");
                                } catch(RecordNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("----- All Policies -----");
                                List<Policy> list = policyService.fetch(userName);
                                list.forEach(System.out::println);
                                break;
                            case 4:
                                try {
                                    System.out.print("Enter Customer ID: ");
                                    int customerId = Integer.parseInt(sc.nextLine());

                                    System.out.print("Enter Customer Name: ");
                                    String customerName = sc.nextLine();

                                    System.out.print("Enter Customer Address: ");
                                    String customerAddress = sc.nextLine();

                                    System.out.print("Enter Age: ");
                                    int age = Integer.parseInt(sc.nextLine());

                                    System.out.print("Enter Aadhaar Number: ");
                                    long aadhaarNumber = Long.parseLong(sc.nextLine());

                                    System.out.print("Enter PAN Number: ");
                                    long panNumber = Long.parseLong(sc.nextLine());

                                    customerService.updatePersonalDetails(customerId, customerName, customerAddress, age, aadhaarNumber, panNumber);
                                    System.out.println("Personal Details Updated Successfully!");
                                } catch (RecordNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 5:
                                try {
                                    System.out.print("Enter Customer ID: ");
                                    int customerId = Integer.parseInt(sc.nextLine());

                                    System.out.print("Enter Registration Number: ");
                                    String regNo = sc.nextLine();

                                    System.out.print("Enter Vehicle Type (TRUCK / MOTORCYCLE / CAMPERVAN): ");
                                    VehicleType vehicleType = VehicleType.valueOf(sc.nextLine().toUpperCase());

                                    vehicleService.addVehicle(customerId, regNo, vehicleType);
                                    System.out.println("Vehicle Added Successfully!");
                                } catch (RecordNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default:
                                System.out.println("Invalid Option");
                        }
                    }
                    break;
                case "OFFICER":
                    System.out.println("Officer Menu");
                    while(true) {
                        System.out.println("1. Update Customer Status");
                        System.out.println("2. Add Review");
                        System.out.println("0. Exit");
                        int op = Integer.parseInt(sc.nextLine());
                        if(op == 0)
                            break;

                        switch(op) {
                            case 1:
                                try {
                                        System.out.print("Enter Policy ID: ");
                                        int policyId = Integer.parseInt(sc.nextLine());

                                        System.out.print("Enter Status (PROPOSAL_SUBMITTED / QUOTE_GENERATED / ACTIVE / EXPIRED): ");
                                        StatusType statusType = StatusType.valueOf(sc.nextLine().toUpperCase());

                                        policyService.updateCustomerStatus(policyId, statusType);
                                        System.out.println("Customer Status Updated Successfully!");

                                } catch (RecordNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                try {
                                    System.out.print("Enter Policy ID: ");
                                    int policyId = Integer.parseInt(sc.nextLine());

                                    System.out.print("Enter Officer ID: ");
                                    int officerId = Integer.parseInt(sc.nextLine());

                                    System.out.print("Enter Action: ");
                                    String action = sc.nextLine();

                                    System.out.print("Enter Comments: ");
                                    String comments = sc.nextLine();

                                    System.out.print("Enter Review Date (yyyy-MM-dd): ");
                                    LocalDate reviewDate = LocalDate.parse(sc.nextLine());

                                    reviewService.addReview(policyId, officerId, action, comments, reviewDate);
                                    System.out.println("Review Added Successfully!");
                                } catch (RecordNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default:
                                System.out.println("Invalid Option");
                        }
                    }
                    break;

                case "ADMIN":
                    System.out.println("Admin Menu");
                    break;
            }
        } catch (RecordNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

    }

    }
