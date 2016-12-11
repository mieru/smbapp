package smbemplsrv.threds.minimalstate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import org.json.JSONArray;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.dbmodel.Towar;
import smbemplsrv.dbmodel.Tranzakcje;
import smbemplsrv.query.ejbcontrol.produkt.ProduktEjbQueryController;
import smbemplsrv.query.ejbcontrol.tranzakcje.TranzakcjeEjbQueryController;

@Stateless
public class MinStateCalculator {
	private HashMap<String,Integer> prodMinStateMap = new HashMap<String,Integer>();
	@EJB
	TranzakcjeEjbQueryController tranzakcjeEjbQueryController;
	
	@EJB
	ProduktEjbCommandController produktEjbCommandController;
	
	@EJB
	ProduktEjbQueryController produktEjbQueryController;

	@Schedule(minute="*/1", hour="*", dayOfWeek="*",
      dayOfMonth="*", month="*", year="*")
    private void calculateMinState(final Timer t) {
        System.out.println("calculateMinState START: " + new Date());
        List<Tranzakcje> listTranzakcje = getTransactionList();
        calcMinStateForEachProduct(listTranzakcje);
        System.out.println("calculateMinState END: " + new java.util.Date());
    }

	private void calcMinStateForEachProduct(List<Tranzakcje> listTranzakcje) {
		for (Tranzakcje tranzakcje : listTranzakcje) {
			calcForProduct(new JSONArray(tranzakcje.getListaProduktow()));
		}

		for (Map.Entry<String, Integer> entry : prodMinStateMap.entrySet()) {
			int tmpVal = (int) Math.round(Math.ceil(entry.getValue() / 5));

			Towar towar = produktEjbQueryController.findEntityByID(Integer.parseInt(entry.getKey()));
			towar.setStanMinSys(tmpVal);
			if (towar.getStanMinimalny() == null || towar.getCzyStanSys()) {
				towar.setStanMinimalny(tmpVal);
			}
			produktEjbCommandController.update(towar);
		}
	}

	private void calcForProduct(JSONArray array) {
		JSONObject jsonObject = null;
		for (int i = 0; i < array.length(); i++) {
			jsonObject = (JSONObject) array.get(i);
			if(prodMinStateMap.containsKey(jsonObject.getString("id"))){
				prodMinStateMap.put(jsonObject.getString("id"), prodMinStateMap.get(jsonObject.getString("id")) + Integer.parseInt(jsonObject.getString("ilosc")));
			}else{
				prodMinStateMap.put(jsonObject.getString("id"), Integer.parseInt(jsonObject.getString("ilosc")));
			}
		}
		
	}

	private List<Tranzakcje> getTransactionList() {
		 	Tranzakcje tranzakcje = new Tranzakcje();
	        tranzakcje.setTyp("S");
	        
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date(System.currentTimeMillis()));
	        
	        StringBuilder condition = new StringBuilder();
	        condition.append("AND (1=0 ");
	        condition.append("OR (date_part('month',data_tanzakcji) = '" + (cal.get(Calendar.MONTH)+1) + "' AND date_part('year',data_tanzakcji) = '"+cal.get(Calendar.YEAR)+"')");
	        cal.add(Calendar.MONTH, -1);
	        condition.append("OR (date_part('month',data_tanzakcji) = '" + (cal.get(Calendar.MONTH)+1) + "' AND date_part('year',data_tanzakcji) = '"+cal.get(Calendar.YEAR)+"')");
	        cal.add(Calendar.YEAR, -1);
	        condition.append("OR (date_part('month',data_tanzakcji) = '" + (cal.get(Calendar.MONTH)+1) + "' AND date_part('year',data_tanzakcji) = '"+cal.get(Calendar.YEAR)+"')");
	        cal.add(Calendar.MONTH, 1);
	        condition.append("OR (date_part('month',data_tanzakcji) = '" + (cal.get(Calendar.MONTH)+1) + "' AND date_part('year',data_tanzakcji) = '"+cal.get(Calendar.YEAR)+"')");
	        cal.add(Calendar.MONTH, 1);
	        condition.append("OR (date_part('month',data_tanzakcji) = '" + (cal.get(Calendar.MONTH)+1) + "' AND date_part('year',data_tanzakcji) = '"+cal.get(Calendar.YEAR)+"')");
	        condition.append(")");
		return tranzakcjeEjbQueryController.findEntity(tranzakcje, condition);
	}
}