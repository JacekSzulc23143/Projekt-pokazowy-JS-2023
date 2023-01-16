package com.example.projektpokazowyjs2023.projects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository; // repozytorium

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // TODO: @Secured("ROLE_PROJECTS_TAB")
    @GetMapping
    ModelAndView index() { // ModelAndView skrót który pomaga pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("projects/index"); // referencja do pliku

        modelAndView.addObject("projects", projectRepository.findAll()); // zwróci listę wszystkich projektów
        // zapisanych w bazie danych

        return modelAndView;
    }
}
