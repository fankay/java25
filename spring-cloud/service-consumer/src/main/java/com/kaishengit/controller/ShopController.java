package com.kaishengit.controller;

import com.kaishengit.client.MovieServiceClient;
import com.kaishengit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shop")
public class ShopController {


    /*@Autowired
    private MovieService movieService;*/

    /*@Autowired
    private RestTemplate restTemplate;*/

    /*@Autowired
    private LoadBalancerClient loadBalancerClient;*/

    @Autowired
    private MovieServiceClient movieServiceClient;

    @GetMapping("/buy/movie/{id}")
    public String buyMovie(@PathVariable Integer id) {
        //return movieService.getMovieNameById(id);
        return movieServiceClient.getMovieName(id);

        /*String url = "http://MOVIE-SERVICE-PROVIDER/movie/{1}";
        return restTemplate.getForObject(url,String.class,id);*/


        //根据服务名称从Eureka上发现服务的提供者，并使用负载均衡的方式返回提供者的地址
        /*ServiceInstance serviceInstance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
        System.out.println("uri:" + serviceInstance.getUri());
        System.out.println("serviceId:" + serviceInstance.getServiceId());
        //String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/movie/"+id;
        String url = serviceInstance.getUri().toString() + "/movie/"+id;
        return restTemplate.getForObject(url,String.class);*/
    }

    @GetMapping("/movie/save")
    public String save() {
        return movieServiceClient.saveNewMovie("Her","toms");
    }

}
