package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.issues.IssueFilter;
import org.springframework.data.domain.Sort;

public class PersonFilter extends IssueFilter {

    // metoda do sortowania
    public String toSortString(Sort sort) {
        Sort.Order order = sort.getOrderFor("realName");
        String sortString = "";
        if (order != null) {
            sortString += "realName," + order.getDirection();
        }

        return sortString;
    }

    // metoda - kierunek sortowania
    public Sort findNextSorting(Sort currentSorting) {
        Sort.Direction currentDirection = currentSorting.getOrderFor("realName") != null ? currentSorting.getOrderFor("realName").getDirection() : null;

        if (currentDirection == null) {
            return Sort.by("realName").ascending();
        } else if (currentDirection.isAscending()) {
            return Sort.by("realName").descending();
        } else {
            return Sort.unsorted();
        }
    }
}
