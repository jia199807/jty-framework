// package com.jty.config;
//
// import com.jty.filter.JwtAuthenticationTokenFilter;
// import com.jty.service.impl.UserDetailsServiceImpl;
// import jakarta.annotation.Resource;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.ProviderManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
// @Configuration
// @EnableWebSecurity// 开启网络安全注解
// @RequiredArgsConstructor
// public class SecurityConfig {
//
//     @Resource
//     UserDetailsServiceImpl userDetailsService;
//
//     @Resource
//     private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//
//     @Resource
//     private AuthenticationEntryPoint authenticationEntryPoint;
//
//     @Resource
//     private AccessDeniedHandler accessDeniedHandler;
//
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 // 关闭csrf
//                 .csrf().disable();
//         http
//                 // 不通过Session获取SecurityContext
//                 .sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//         http
//                 .authorizeRequests()
//                 // 对于登录接口 允许匿名访问
//                 .requestMatchers("/login").anonymous()
//                 // 注销接口需要认证才能访问
//                 .requestMatchers("/logout").authenticated()
//                 // 个人信息接口必须登录后才能访问
//                 .requestMatchers("/user/userInfo").authenticated()
//                 // 除上面外的所有请求全部不需要认证即可访问
//                 .anyRequest().permitAll();
//         // 允许跨域
//         http
//                 .cors();
//
//         // 把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
//         http
//                 .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//         // 配置异常处理器
//         http
//                 .exceptionHandling()
//                 // 认证失败处理器
//                 .authenticationEntryPoint(authenticationEntryPoint)
//                 // 授权失败处理器
//                 .accessDeniedHandler(accessDeniedHandler);
//
//         // 关闭默认的注销功能
//         http
//                 .logout().disable();
//
//         return http.build();
//     }
//
//
//     @Bean
//     AuthenticationManager authenticationManager() {
//         DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//         daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//         ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
//         return pm;
//     }
//
//     @Bean
//     PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//
//     // @Bean
//     // JsonLoginFilter jsonLoginFilter() {
//     //     JsonLoginFilter filter = new JsonLoginFilter();
//     //     filter.setAuthenticationSuccessHandler((req, resp, auth) -> {
//     //         resp.setContentType("application/json;charset=utf-8");
//     //         PrintWriter out = resp.getWriter();
//     //
//     //
//     //         // 获取userid 生成token
//     //         LoginUser loginUser = (LoginUser) auth.getPrincipal();
//     //         User user = loginUser.getUser();
//     //         String userId = loginUser.getUser().getId().toString();
//     //
//     //         String jwtToken = JwtUtil.createJWT(userId);
//     //
//     //         // 把用户信息存入redis
//     //         redisCache.setCacheObject("bloglogin:" + userId, loginUser);
//     //
//     //         // 把User转换成UserInfoVo
//     //         UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
//     //
//     //         // 把token和userInfoVo封装 返回
//     //         BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwtToken, userInfoVo);
//     //         ResponseResult result = ResponseResult.okResult(blogUserLoginVo);
//     //
//     //         out.write(JSON.toJSONString(result));
//     //
//     //         // //获取当前登录成功的用户对象
//     //         // User user = (User) auth.getPrincipal();
//     //         // user.setPassword(null);
//     //         // ResponseResult result = ResponseResult.okResult("登录成功", user);
//     //         // out.write(new ObjectMapper().writeValueAsString(result));
//     //     });
//     //     filter.setAuthenticationFailureHandler((req, resp, e) -> {
//     //         resp.setContentType("application/json;charset=utf-8");
//     //         PrintWriter out = resp.getWriter();
//     //         e.printStackTrace();
//     //
//     //         ResponseResult result = ResponseResult.errorResult("用户名或者密码输入错误，登录失败");
//     //         // if (e instanceof BadCredentialsException) {
//     //         //     result.setMsg("用户名或者密码输入错误，登录失败");
//     //         // } else if (e instanceof DisabledException) {
//     //         //     result.setMsg("账户被禁用，登录失败");
//     //         // } else if (e instanceof CredentialsExpiredException) {
//     //         //     result.setMsg("密码过期，登录失败");
//     //         // } else if (e instanceof AccountExpiredException) {
//     //         //     result.setMsg("账户过期，登录失败");
//     //         // } else if (e instanceof LockedException) {
//     //         //     result.setMsg("账户被锁定，登录失败");
//     //         // }
//     //         out.write(JSON.toJSONString(result));
//     //     });
//     //     filter.setAuthenticationManager(authenticationManager());
//     //
//     //     filter.setFilterProcessesUrl("/login");
//     //     filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
//     //     return filter;
//     // }
// }