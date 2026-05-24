package com.service;

import com.Exception.RecordNotFoundException;
import com.model.InsuranceTeam;
import com.model.Policy;
import com.model.Review;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;

public class ReviewService {
    private final Session session;

    public ReviewService(Session session) {
        this.session = session;
    }

    public void addReview(int policyId, int officerId, String action, String comments, LocalDate reviewDate) {
        Transaction tx = session.beginTransaction();
        Policy policy = session.find(Policy.class, policyId);

        if (policy == null) {
            tx.commit();
            throw new RecordNotFoundException("Invalid Policy ID given!!");
        }

        InsuranceTeam insuranceTeam = session.find(InsuranceTeam.class, officerId);

        if (insuranceTeam == null) {
            tx.commit();
            throw new RecordNotFoundException("Invalid Officer ID given!!");
        }

        Review review = new Review();
        review.setPolicy(policy);
        review.setInsuranceTeam(insuranceTeam);
        review.setAction(action);
        review.setComments(comments);
        review.setReviewDate(reviewDate);

        session.persist(review);
        tx.commit();
    }
}
