package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.projects.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

// Klasa którą przekazujemy z widoku do kontrolera i z kontrolera do widoku
@Data // Gettery i settery itp
@NoArgsConstructor // bezargumentowy konstruktor
@AllArgsConstructor // konstruktor ze wszystkimi argumentami
public class IssueFilter {

    private Project project;

    private IssueState state;

    private Person contractor;

    private Issue enabled;


    // filtrowanie
    private Specification<Issue> isEnabled(){
        return (root, query, builder) -> builder.equal(root.get("enabled"), true);
    }

    private Specification<Issue> hasProject() {
        return (root, query, builder) -> builder.equal(root.get("project"), project);
    }

    private Specification<Issue> hasState() {
        return (root, query, builder) -> builder.equal(root.get("state"), state);
    }

    private Specification<Issue> hasContractor() {
        return (root, query, builder) -> builder.equal(root.get("contractor"), contractor);
    }

    public Specification<Issue> buildQuery() {
        Specification<Issue> spec = Specification.where(isEnabled());

        if (project != null) {
            spec = spec.and(hasProject());
        }

        if (state != null) {
            spec = spec.and(hasState());
        }

        if (contractor != null) {
            spec = spec.and(hasContractor());
        }

        return spec;
    }

    // paginacja i sortowanie
    public String toQueryString(Integer page, Sort sort) {
        return "page=" + page +
                "&sort=" + toSortString(sort) +
                (project != null ? "&project=" + project.getId() : "") +
                (state != null ? "&state=" + state : "") +
                (contractor != null ? "&contractor=" + contractor.getId() : "");
//                (enabled != null ? "&enabled=" + enabled : "");
    }

    // metoda do sortowania
    public String toSortString(Sort sort) {
        Sort.Order order = sort.getOrderFor("name");
        String sortString = "";
        if (order != null) {
            sortString += "name," + order.getDirection();
        }

        return sortString;
    }

    // metoda - kierunek sortowania
    public Sort findNextSorting(Sort currentSorting) {
        Sort.Direction currentDirection = currentSorting.getOrderFor("name") != null ? currentSorting.getOrderFor("name").getDirection() : null;

        if (currentDirection == null) {
            return Sort.by("name").ascending();
        } else if (currentDirection.isAscending()) {
            return Sort.by("name").descending();
        } else {
            return Sort.unsorted();
        }
    }
}