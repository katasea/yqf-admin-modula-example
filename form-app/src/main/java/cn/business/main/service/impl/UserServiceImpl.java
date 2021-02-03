package cn.business.main.service.impl;



import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

//REDIS缓存调用示例

@Deprecated
@Service
@CacheConfig(cacheNames = "user") //指定cache的名字,这里指定了 caheNames，下面的方法的注解里就可以不用指定 value 属性了
public class UserServiceImpl {

//	private Map<Long, User> userMap = new HashMap<Long, User>();
//
//	public UserServiceImpl() {
//		User u1=new User();
//		u1.setId(1L);
//		u1.setName("1111");
//		u1.setPassword("11223434");
//		User u2=new User();
//		u2.setId(2L);
//		u2.setName("1111");
//		u2.setPassword("11223434");
//		User u3=new User();
//		u3.setId(3L);
//		u3.setName("1111");
//		u3.setPassword("11223434");
//
//		userMap.put(1L,u1);
//		userMap.put(2L,u2);
//		userMap.put(3L,u3);
//	}
//
//	@Cacheable()
//	@Override
//	public List<User> list() {
//		System.out.println("querying list.....");
//		User[] users = new User[userMap.size()];
//		this.userMap.values().toArray(users);
//		return Arrays.asList(users);
//	}
//
//	@Cacheable(key = "'user:'.concat(#id.toString())")
//	@Override
//	public User findUserById(Long id) {
//		System.out.println("没有调用缓存");
//		return userMap.get(id);
//	}
//
//
//	@Cacheable(key = "'user:'.concat(#user.id)")
//	@Override
//	public void update(User user) {
//		System.out.println("没有调用缓存");
//		userMap.put(user.getId(), user);
//	}
//
//	// 清空cache
//	@CacheEvict(key = "'user:'.concat(#id.toString())")
//	@Override
//	public void remove(Long id) {
//		System.out.println("没有调用缓存");
//		userMap.remove(id);
//	}
//
//	@CacheEvict(key = "'user:'.concat(#id.toString())")
//	@Override
//	public User upuser(Long id) {
//		System.out.println("没有调用缓存");
//		User d = userMap.get(id);
//		d.setName("0000000000000000000000000000000000000000");
//		return d;
//	}
//
//	@CachePut(key = "'user:'.concat(#user.id)")
//	@Override
//	public User saveUser(User user) {
//		System.out.println("没有调用缓存");
//		userMap.put(user.getId(), user);
//		return user;
//	}

}
