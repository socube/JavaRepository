package cn.ccsu.main;

import redis.clients.jedis.Jedis;

public class JedisJava {
	public static void main(String[] args) {
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		System.out.println("���ӳɹ�");
		// �鿴�����Ƿ�����
		System.out.println("������������: " + jedis.ping());
		System.out.println(jedis.keys("*"));
		jedis.close();
	}
}