package sg.edu.nus.iss.workshop13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.workshop13.model.Contact;
import sg.edu.nus.iss.workshop13.util.Contacts;

@Controller
@RequestMapping(path="/addressbook")
public class addressbookController {

    @Autowired
    private Contacts contacts;

    @Autowired
    private ApplicationArguments appArgs;

    // store the default directory path at application.properties
    @Value("${workshop13.default.data.dir}")
    private String defaultDataDir;

    @GetMapping
    public String showAddressBookForm(Model model){
        model.addAttribute("contact",new Contact());
        return "addressBook";
    }

    @PostMapping
    public String saveContact(@Valid Contact contact, BindingResult binding, Model model){
        if(binding.hasErrors()){
            return "addressBook";
        }
        contacts.saveContact(contact, model, appArgs, defaultDataDir);
        return "showContact";
    }

    @GetMapping(path="{contactId}")
    public String getContactId(Model model, @PathVariable String contactId){
        contacts.getContactById(model, contactId, appArgs, defaultDataDir);
        return "showContact";
    }

    @GetMapping(path="/list")
    public String getAllContacts(Model model){
        contacts.getAllContact(model, appArgs, defaultDataDir);
        return "contactsPage";
    }
}
