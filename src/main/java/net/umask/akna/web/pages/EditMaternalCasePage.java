package net.umask.akna.web.pages;

import net.umask.akna.commands.SaveOrUpdateMaternalCaseCommand;
import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.web.MaternalWicketApplication;
import net.umask.akna.web.components.TinyMceEnabler;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 23-nov-2009
 * Time: 20:14:14
 */
@AuthorizeInstantiation({"ROLE_ADMIN"})
public class EditMaternalCasePage extends BasePage {
    public EditMaternalCasePage(MaternalCaseDTO maternalCase) {
        add(new EditMaternalCaseForm("form", new MaternalCaseLoadableDetachableModel(maternalCase)));
    }

    public EditMaternalCasePage(IModel<MaternalCaseDTO> rowModel) {
        add(new EditMaternalCaseForm("form", rowModel));
    }

    private class EditMaternalCaseForm extends Form<MaternalCaseDTO> {
        public EditMaternalCaseForm(String s, IModel<MaternalCaseDTO> maternalCaseLoadableDetachableModel) {
            super(s, new CompoundPropertyModel<MaternalCaseDTO>(maternalCaseLoadableDetachableModel));
            add(new RequiredTextField("title"));
            add(new TextArea<String>("body", new PropertyModel<String>(maternalCaseLoadableDetachableModel, "body")).add(new TinyMceEnabler()));
        }

        @Override
        protected void onSubmit() {
            MaternalCaseDTO matenalCase = getModelObject();
            MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new SaveOrUpdateMaternalCaseCommand(matenalCase.getId(), matenalCase.getTitle(), matenalCase.getBody()));
            setResponsePage(ListCases.class);
        }
    }
}
