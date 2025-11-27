package org.example;

public class StudentRatingService implements RatingService {

    public int calcRating(int sum) {
        return sum + 5;
    }
}
