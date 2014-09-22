package org.fossa.rolp.ui.fach.fachdefinition;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class FachdefinitionenVerwalten extends FossaWindow {

	private static final long serialVersionUID = 4385297032942539348L;
	
	private FachdefinitionenVerwaltenForm formFachdefinitionenVerwalten;
	private FachdefinitionLaso fachdefinitionLaso;

	public FachdefinitionenVerwalten(RolpApplication app, FachdefinitionLaso fachdefinition) {
		super(app);
		this.fachdefinitionLaso = fachdefinition;
		setCaption(" - Fachdefinition - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getFachdefinitionenVerwaltenForm();
		layout.addComponent(formFachdefinitionenVerwalten, "form");
	}
	
	private FachdefinitionenVerwaltenForm getFachdefinitionenVerwaltenForm() {
		if (formFachdefinitionenVerwalten == null) {
			formFachdefinitionenVerwalten = new FachdefinitionenVerwaltenForm();
			if (fachdefinitionLaso.getId() == null) {
				formFachdefinitionenVerwalten.addTemporaryItem(fachdefinitionLaso);
			} else {
				formFachdefinitionenVerwalten.setFachdefinition(fachdefinitionLaso);
			}
		}
		return formFachdefinitionenVerwalten;
	}
	
	@Override
	public void unlockLaso() {
		if (fachdefinitionLaso != null && !formFachdefinitionenVerwalten.isReadOnly()) {
			((FossaLaso) fachdefinitionLaso).unlock();
		}
	}

}
