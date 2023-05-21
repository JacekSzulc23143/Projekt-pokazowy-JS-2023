package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.issues.IssueFilter;
import org.springframework.data.domain.Sort;

public class PersonFilter extends IssueFilter {

    // metoda do sortowania
    public String toSortString(Sort sort) {
        Sort.Order order = sort.getOrderFor("username");
        String sortString = "";
        if (order != null) {
            sortString += "username," + order.getDirection();
        }

        return sortString;
    }

    // metoda - kierunek sortowania
    public Sort findNextSorting(Sort currentSorting) {
        Sort.Direction currentDirection = currentSorting.getOrderFor("username") != null ? currentSorting.getOrderFor("username").getDirection() : null;

        if (currentDirection == null) {
            return Sort.by("username").ascending();
        } else if (currentDirection.isAscending()) {
            return Sort.by("username").descending();
        } else {
            return Sort.unsorted();
        }
    }
}
