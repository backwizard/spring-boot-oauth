package com.example.service;

import static com.example.service.ServiceConstants.HIGH_SALARY;
import static com.example.service.ServiceConstants.LOW_SALARY;
import static com.example.service.ServiceConstants.MID_SALARY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.MemberType;
import com.example.model.User;
import com.example.model.UserDetail;
import com.example.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User saveUser(User user) throws RuntimeException {
		user.setMemberType(mapSalary(user.getSalary()));
		user.setRefCode(generateRefCode(user.getPhone()));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Dupplicate data!");
		}
	}

	protected String generateRefCode(String phone) {
		String dateNow = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		return dateNow.concat(phone.substring(phone.length() - 3));
	}

	private MemberType mapSalary(BigDecimal salary) {
		if (salary.compareTo(HIGH_SALARY) == 1) {
			return MemberType.Platinum;
		} else if (salary.compareTo(HIGH_SALARY) == 0
				&& (salary.compareTo(MID_SALARY) == 1 || salary.compareTo(MID_SALARY) == 0)) {
			return MemberType.Gold;
		} else if (salary.compareTo(MID_SALARY) == -1
				&& (salary.compareTo(LOW_SALARY) == 0 || salary.compareTo(LOW_SALARY) == 1)) {
			return MemberType.Silver;
		} else {
			throw new IllegalArgumentException("Too less salary!");
		}
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUsername(username);
		userOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return userOptional.map(UserDetail::new).get();
	}

}
