package facade.kategoriatowar;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.KategoriaTowar;
import query.ejbcontrol.kategoriatowar.KategoriaTowarEjbQueryController;

@Stateless
@LocalBean
public class KategoriaTowarFacade {
	private static final String PN_NAZWA = "nazwa";
	private static final String PN_ID = "id";

	@EJB
	KategoriaTowarEjbQueryController kategoriaTowarEjbQueryController;

	public String getAll() throws JSONException {
		JSONArray jsonArray = new JSONArray();

		List<KategoriaTowar> katCol = kategoriaTowarEjbQueryController.findAll();
		
		JSONObject jsonObject = null;
		for (KategoriaTowar kategoriaTowar : katCol) {
			jsonObject = new JSONObject();
			jsonObject.put(PN_NAZWA, kategoriaTowar.getNazwa());
			jsonObject.put(PN_ID, kategoriaTowar.getIdKatTowar());
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
}
