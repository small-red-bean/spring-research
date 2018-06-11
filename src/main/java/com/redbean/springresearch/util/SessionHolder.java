package com.redbean.springresearch.util;


import java.util.Random;

public class SessionHolder {
  private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

  public static String generateSessionId() 
  {
    Random rand = new Random();
    StringBuffer sid = new StringBuffer();

    sid.append(System.currentTimeMillis());

    for (int i = 0; i < 5; i++)
    {
      sid.append((char)(97 + rand.nextInt(26)));
    }

    THREAD_LOCAL.set(sid.toString());

    return sid.toString();
  }

  public static String getSessionId() 
  {
    String sid = (String)THREAD_LOCAL.get();
    if ((sid != null) && (!sid.isEmpty())) return sid;

    return generateSessionId();
  }

  public static void useSessionId(String sid) 
  {
    if ((sid != null) && (!sid.isEmpty()))
      THREAD_LOCAL.set(sid);
  }
}