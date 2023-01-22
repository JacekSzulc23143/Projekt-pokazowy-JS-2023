package com.example.projektpokazowyjs2023.projects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    ModelAndView index() { // ModelAndView skrót, który pomaga pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("projects/index"); // referencja do pliku

        modelAndView.addObject("projects", projectRepository.findAll()); // zwróci listę wszystkich projektów
        // zapisanych w bazie danych
        return modelAndView;
    }

    // wyświetlenie formularza
    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = new Project();
        modelAndView.addObject("project", project);

        return modelAndView;
    }

    // edycja formularza

    /**
     * https://www.baeldung.com/spring-boot-crud-thymeleaf
     */
    @GetMapping("/edit/{id}")
    // TODO: @Secured("ROLE_PROJECT_EDIT")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = projectRepository.findById(id).orElse(null);

        modelAndView.addObject("project", project);

        return modelAndView;
    }

    // wysłanie formularza do akcji save

    /**
     * Dokumentacja -> https://spring.io/guides/gs/handling-form-submission/
     */
    @PostMapping("/save")
    String save(@ModelAttribute Project project) {

        boolean isNew = project.getId() == null; // sprawdza czy nowy projekt

        projectRepository.save(project);

        if (isNew) { // podejmuje decyzje dokąd przenieść
            return "redirect:/projects";
        } else {
            return "redirect:/projects/edit/" + project.getId();
        }
    }

    // usuwanie projektu
    @GetMapping("/delete/{id}")
    ModelAndView deleteProject(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = projectRepository.findById(id).orElse(null);

        project.setEnabled(false);

        projectRepository.delete(project);

        modelAndView.setViewName("redirect:/projects");

        return modelAndView;
    }
}
