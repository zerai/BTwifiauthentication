package org.bobarctor.Rm3Wifi.Persistence;

import org.bobarctor.Rm3Wifi.Exceptions.LoadDataException;
import org.bobarctor.Rm3Wifi.Exceptions.SaveDataException;
import org.bobarctor.Rm3Wifi.Model.Data;

public interface DataDAO {
	/**
	 * @author Michele Valentini
	 * 
	 */
	public Data loadData() throws LoadDataException;
	public void saveData(Data data) throws SaveDataException;

}
