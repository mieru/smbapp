package smbemplsrv.facade.produkt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.command.ejbcontrol.towarimage.TowarImageEjbCommandController;
import smbemplsrv.command.restaction.produkt.ProduktRequestData;
import smbemplsrv.dbmodel.KategoriaTowar;
import smbemplsrv.dbmodel.Magazyn;
import smbemplsrv.dbmodel.Towar;
import smbemplsrv.dbmodel.TowarImage;
import smbemplsrv.query.ejbcontrol.katprod.KatProdEjbQueryController;
import smbemplsrv.query.ejbcontrol.magazyn.MagazynEjbQueryController;
import smbemplsrv.query.ejbcontrol.produkt.ProduktEjbQueryController;
import smbemplsrv.query.ejbcontrol.towarimage.TowarImageEjbQueryController;
import smbemplsrv.query.restaction.produkt.ProduktRequestQueryData;

@Stateless
@LocalBean
public class ProduktFacade {

	@EJB
	ProduktEjbCommandController produktEjbCommandController;
	@EJB
	ProduktEjbQueryController produktEjbQueryController;
	@EJB
	MagazynEjbQueryController magazynEjbQueryController;
	@EJB
	KatProdEjbQueryController katProdEjbQueryController;
	@EJB
	TowarImageEjbCommandController towarImageEjbCommandController;
	@EJB
	TowarImageEjbQueryController towarImageEjbQueryController;
	
	
	
	public String getProdukty(ProduktRequestQueryData produktRequestData) throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		Integer idMagazynu = null;
		Magazyn magazyn = null;
		List<Towar> towarList = null;
		if(produktRequestData.idMagazynu != null){
			idMagazynu = produktRequestData.idMagazynu;
			magazyn = magazynEjbQueryController.findEntityByID(idMagazynu);
			towarList = magazyn.getTowars();
		}else{
			towarList = produktEjbQueryController.findAll(); 
		}
		
		for (Towar t : towarList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lp", towarList.indexOf(t));
			jsonObject.put("nazwa", t.getNazwa());
			jsonObject.put("id", t.getIdTowaru());
			     File filename = new File(System.getProperty("jboss.server.data.dir").toString() + t.getTowarImage().getImage());
			     String base64 = getStringImage(filename);
			     
			     if(t.getStanMinimalny() != null && t.getIloscWMagazynie() != null && t.getStanMinimalny() > t.getIloscWMagazynie()){
			    	 jsonObject.put("fontColor","red");
			     }else{
			    	 jsonObject.put("fontColor","black");
			     }
			
			jsonObject.put("image",base64);
			jsonObject.put("ilosc",t.getIloscWMagazynie());
			jsonObject.put("iloscZakup",1);
			jsonObject.put("stan_min",t.getStanMinimalny());
			jsonObject.put("opis", t.getOpis());
			jsonObject.put("cnetto", t.getCenaNetto());
			jsonObject.put("stawka_vat", t.getStawkaVat());
			jsonObject.put("cbrutto", t.getCenaNetto() * (1.00 + t.getStawkaVat() * 0.01));
			jsonObject.put("jednostka", t.getJednostka());
			jsonArray.put(jsonObject);
			
		}
		return jsonArray.toString();
	}
	
	public String getProduktById(ProduktRequestQueryData produktRequestData) {
		Towar t = produktEjbQueryController.findEntityByID(produktRequestData.idProduktu);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", t.getNazwa());
		jsonObject.put("id", t.getIdTowaru());
		     File filename = new File(System.getProperty("jboss.server.data.dir").toString() + t.getTowarImage().getImage());
		     String base64 = getStringImage(filename);
		     
		jsonObject.put("image","data:image/jpeg;base64,"+base64);
		jsonObject.put("ilosc",t.getIloscWMagazynie());
		jsonObject.put("stan_min",t.getStanMinimalny());
		jsonObject.put("stan_min_uzyt",t.getStanMinUzyt());
		jsonObject.put("stan_min_sys",t.getStanMinSys());
		jsonObject.put("opis", t.getOpis());
		jsonObject.put("kat", t.getKategoriaTowar().getIdKatTowar().toString());
		jsonObject.put("id_magazynu", t.getMagazyn().getIdMagazynu().toString());
		jsonObject.put("cnetto", t.getCenaNetto());
		jsonObject.put("stawka_vat", t.getStawkaVat());
		jsonObject.put("cbrutto", t.getCenaNetto() * (1.00 + t.getStawkaVat() * 0.01));
		jsonObject.put("jednostka", t.getJednostka());
		jsonObject.put("czyStanUzyt", t.getCzyStanUzyt());
		jsonObject.put("czyStanSys", t.getCzyStanSys());
		
		return jsonObject.toString();
	}


	public String dodajProdukt(ProduktRequestData produktRequestData) throws FileNotFoundException, IOException {
		Towar towar = new Towar();
		towar.setCenaNetto(produktRequestData.cnetto);
		towar.setIloscWMagazynie(produktRequestData.ilosc);
		towar.setJednostka(produktRequestData.jednostka);
		towar.setNazwa(produktRequestData.name);
		towar.setOpis(produktRequestData.opis);
		towar.setStawkaVat(produktRequestData.stawkaVat);
		towar.setStanMinUzyt(produktRequestData.stan_min);
		towar.setPowiadomiono(false);
		towar.setCzyStanSys(true);
		towar.setCzyStanUzyt(false);
		towar.setStanMinSys(1);
		if(produktRequestData.stan_min != null){
			towar.setStanMinimalny(produktRequestData.stan_min);
		}else{
			towar.setStanMinimalny(1);
		}
		
		
		
		Magazyn magazyn = magazynEjbQueryController.findEntityByID(produktRequestData.idMagazynu);
		KategoriaTowar kategoriaTowar = katProdEjbQueryController.findEntityByID(produktRequestData.kat);
		
		towar.setMagazyn(magazyn);
		towar.setKategoriaTowar(kategoriaTowar);
		
		towar = produktEjbCommandController.insert(towar);
		byte[] data = Base64.getDecoder().decode(produktRequestData.image.split(",")[1]);
		try (OutputStream stream = new FileOutputStream(System.getProperty("jboss.server.data.dir").toString() + "\\zdjecia\\produkt" + towar.getIdTowaru() + ".jpg")) {
		    stream.write(data);
		}
		TowarImage towarImage = new TowarImage();
		towarImage.setImage("\\zdjecia\\produkt" + towar.getIdTowaru() + ".jpg");
		towarImage = towarImageEjbCommandController.insert(towarImage);
		
		towar.setTowarImage(towarImage);
		towar = produktEjbCommandController.update(towar);
		
		return "";
	}


	public String usunProdukt(ProduktRequestData produktRequestData) {
	    Towar towar = produktEjbQueryController.findEntityByID(produktRequestData.id);
	    produktEjbCommandController.delete(towar);
		return "";
	}

	private String getStringImage(File file){
	    try {
	        FileInputStream fin = new FileInputStream(file);
	        byte[] imageBytes = new byte[(int)file.length()];
	        fin.read(imageBytes, 0, imageBytes.length);
	        fin.close();
	        return Base64.getEncoder().encodeToString(imageBytes);
	    } catch (Exception ex) {
	    }
	    return null;
	}

	public String edytujProdukt(ProduktRequestData produktRequestData) throws FileNotFoundException, IOException {
		Towar towar = produktEjbQueryController.findEntityByID(produktRequestData.id);
		towar.setCenaNetto(produktRequestData.cnetto);
		towar.setIloscWMagazynie(produktRequestData.ilosc);
		towar.setJednostka(produktRequestData.jednostka);
		towar.setNazwa(produktRequestData.name);
		towar.setOpis(produktRequestData.opis);
		towar.setStawkaVat(produktRequestData.stawkaVat);
		towar.setStanMinUzyt(produktRequestData.stan_min_uzyt);
		
		if(produktRequestData.czyStanUzyt != null && produktRequestData.czyStanUzyt){
			towar.setStanMinimalny(produktRequestData.stan_min_uzyt);
			towar.setCzyStanSys(false);
			towar.setCzyStanUzyt(true);
		}
		if(produktRequestData.czyStanSys != null && produktRequestData.czyStanSys){
			towar.setStanMinimalny(produktRequestData.stan_min_sys);
			towar.setCzyStanSys(true);
			towar.setCzyStanUzyt(false);
		}
		
		
		Magazyn magazyn = magazynEjbQueryController.findEntityByID(produktRequestData.idMagazynu);
		KategoriaTowar kategoriaTowar = katProdEjbQueryController.findEntityByID(produktRequestData.kat);
		
		towar.setMagazyn(magazyn);
		towar.setKategoriaTowar(kategoriaTowar);
		
		byte[] data = Base64.getDecoder().decode(produktRequestData.image.split(",")[1]);
		try (OutputStream stream = new FileOutputStream(System.getProperty("jboss.server.data.dir").toString() + "\\zdjecia\\produkt" + towar.getIdTowaru() + ".jpg")) {
		    stream.write(data);
		}
		
		produktEjbCommandController.update(towar);
		
		return "";
	}
	
}
