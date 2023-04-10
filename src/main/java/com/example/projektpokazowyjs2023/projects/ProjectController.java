package com.example.projektpokazowyjs2023.projects;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository; // repozytorium

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // TODO: @Secured("ROLE_PROJECTS_TAB")
    @GetMapping
    ModelAndView index(Pageable pageable) { // ModelAndView skrót, który pomaga pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("projects/index"); // referencja do pliku

        modelAndView.addObject("projects", projectRepository.findAll(pageable)); // zwróci listę wszystkich projektów
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
    // TODO: @Secured("ROLE_PROJECT_EDIT")
    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = projectRepository.findById(id).orElse(null);

        modelAndView.addObject("project", project);

        return modelAndView;
    }

    // wysłanie formularza do akcji save
    // dostęp do błędów przez klasę BindingResult
    // komunikaty przez klasę RedirectAttributes
    /**
     * Dokumentacja -> https://spring.io/guides/gs/handling-form-submission/
     */
    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Project project, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("projects/create");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("project", project);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }

        boolean isNew = project.getId() == null; // sprawdza czy nowy projekt

        projectRepository.save(project);

        redirectAttrs.addFlashAttribute("status", "success");

        if (isNew) { // podejmuje decyzje dokąd przenieść
            modelAndView.setViewName("redirect:/projects");
        } else {
            modelAndView.setViewName("redirect:/projects/edit/" + project.getId());
        }

//        // zmień widok na:
//        modelAndView.setViewName("redirect:/projects");

        return modelAndView;
    }

    // usuwanie projektu
    // komunikaty przez klasę RedirectAttributes
    @GetMapping("/delete/{id}")
    ModelAndView deleteProject(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = projectRepository.findById(id).orElse(null);

        project.setEnabled(false);

        projectRepository.delete(project);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/projects");

        return modelAndView;
    }
}
