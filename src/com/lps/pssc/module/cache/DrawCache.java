package com.lps.pssc.module.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DrawCache {
	private static volatile DrawCache instance = null;
	private static Object lockHelper = new Object();

	public static DrawCache getInstance() {
		if (instance == null) {
			synchronized (lockHelper) {
				if (instance == null) {
					instance = new DrawCache();
				}
			}
		}
		return instance;
	}
	
	private volatile Boolean stop = false;
	private ConcurrentHashMap<String, DrawCacheData> dict;
	private List<String> list;
	public String ConnectionString;

	private DrawCache() {
		list = new ArrayList<String>();
		dict = new ConcurrentHashMap<String, DrawCacheData>();
		//Thread thread = new Thread(new ThreadStart(deamon));
		//thread.Start();
	}

	public void push(String studentUid, String cursewareUid, String data)
    {
        String key = studentUid.toLowerCase() + "-" + cursewareUid.toLowerCase();
        
        DrawCacheData cacheData = null;
        if(dict.containsKey(key)) {
        	cacheData = dict.get(key);
        } else {
            cacheData = new DrawCacheData();
            dict.put(key, cacheData);
        }
        cacheData.push(data);

        synchronized (list)
        {
            key = studentUid.toLowerCase() + ";" + cursewareUid.toLowerCase() + ";" + data;
            list.add(key);
        }
    }

	public String poll(String studentUid, String cursewareUid, long tick)
    {
        String result = "";
        String key = studentUid.toLowerCase() + "-" + cursewareUid.toLowerCase();
        if(dict.containsKey(key)) {
        	DrawCacheData cacheData = dict.get(key);
        	result = cacheData.poll(tick);
        }
        return result;
    }

	public void stopDeamon() {
		stop = true;
	}

	private void deamon()
    {
        /*while (!stop)
        {
            DrawCacheData[] caches = null;
            lock (dict)
            {
                if (dict.Values.Count > 0)
                {
                    caches = new DrawCacheData[dict.Values.Count];
                    dict.Values.CopyTo(caches, 0);
                }
            }
            if (caches != null)
            {
                foreach (DrawCacheData cacheData in caches)
                {
                    cacheData.Tick();
                }
            }

            String[] array = null;
            lock (list)
            {
                if (list.Count > 0)
                {
                    array = list.ToArray();
                    list.Clear();
                }
            }

            if (array != null)
            {
                database(array);
            }

            Thread.Sleep(1000);
        }*/
    }

	private void database(String[] array)
    {
        /*if (String.IsNullOrEmpty(ConnectionString))
        {
            return;
        }

        try
        {
            using (SqlConnection conn = new SqlConnection(ConnectionString))
            {
                conn.Open();
                using (SqlCommand cmd = conn.CreateCommand())
                {
                    cmd.CommandText = "insert into UI_DrawRecord(UI_Student_UID, UI_Curseware_UID, DrawDate, CreateDate) values(@UI_Student_UID, @UI_Curseware_UID, @DrawDate, @CreateDate)";
                    SqlParameter studentUid = cmd.Parameters.Add("@UI_Student_UID", SqlDbType.UniqueIdentifier);
                    SqlParameter cursewareUid = cmd.Parameters.Add("@UI_Curseware_UID", SqlDbType.UniqueIdentifier);
                    SqlParameter drawData = cmd.Parameters.Add("@DrawDate", SqlDbType.NVarChar, 500);
                    SqlParameter createDate = cmd.Parameters.Add("@CreateDate", SqlDbType.DateTime);
                    foreach (String items in array)
                    {
                        String[] item = items.Split(";".ToCharArray());
                        if (item.Length >= 3)
                        {
                            studentUid.Value = new Guid(item[0]);
                            cursewareUid.Value = new Guid(item[1]);
                            createDate.Value = DateTime.Now;
                            for (int i = 2; i < item.Length; ++i)
                            {
                                drawData.Value = item[i];
                                cmd.ExecuteNonQuery();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception)
        {

        }*/
    }
}