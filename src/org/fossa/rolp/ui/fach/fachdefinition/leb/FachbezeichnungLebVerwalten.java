package org.fossa.rolp.ui.fach.fachdefinition.leb;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebLaso;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class FachbezeichnungLebVerwalten extends FossaWindow {

	private static final long serialVersionUID = -370969049305768113L;
	
	private FachbezeichnungLebVerwaltenForm formFachdefinitionenLebVerwalten;
	private FachbezeichnungLebLaso fachdefinitionLebLaso;

	public FachbezeichnungLebVerwalten(RolpApplication app, FachbezeichnungLebLaso fachdefinitionLeb, FachdefinitionPojo fachdefinition) {
		super(app);
		this.fachdefinitionLebLaso = fachdefinitionLeb;
		setCaption(" - alternative Fachbezeichnungen für " + fachdefinition.getFachbezeichnung() + " - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getFachdefinitionLebVerwaltenForm(fachdefinition);
		layout.addComponent(formFachdefinitionenLebVerwalten, "form");
	}
	
	private FachbezeichnungLebVerwaltenForm getFachdefinitionLebVerwaltenForm(FachdefinitionPojo fachdefinition) {
		if (formFachdefinitionenLebVerwalten == null) {
			formFachdefinitionenLebVerwalten = new FachbezeichnungLebVerwaltenForm(fachdefinition);
			if (fachdefinitionLebLaso.getId() == null) {
				formFachdefinitionenLebVerwalten.addTemporaryItem(fachdefinitionLebLaso);
			} else {
				formFachdefinitionenLebVerwalten.setFachdefinitionLeb(fachdefinitionLebLaso);
			}
		}
		return formFachdefinitionenLebVerwalten;
	}
	
	@Override
	public void unlockLaso() {
		if (fachdefinitionLebLaso != null && !formFachdefinitionenLebVerwalten.isReadOnly()) {
			((FossaLaso) fachdefinitionLebLaso).unlock();
		}
	}

}
