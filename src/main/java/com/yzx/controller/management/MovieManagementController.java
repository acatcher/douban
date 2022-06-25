package com.yzx.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yzx.entity.Movie;
import com.yzx.service.MovieService;
import com.yzx.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MovieManagementController {

    @Resource
    MovieService movieService;

    /**
     * wangEditor文件上传
     *
     * @param file    上传文件
     * @param request 原生请求对象
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        //得到上传目录
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        //文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    /**
     * 分页查询图书数据
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Movie> pageObject = movieService.getPage(null, null, page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());//当前页面数据
        result.put("count", pageObject.getTotal());//未分页时记录总数
        return result;
    }


    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Movie movie) {
        Map result = new HashMap();
        try {
            movie.setEvaluationQuantity(0);
            movie.setEvaluationScore(0f);
            Document doc = Jsoup.parse(movie.getDescription());//解析图书详情
            Element img = doc.select("img").first();//获取图书详情第一图的元素对象
            String cover = img.attr("src");
            movie.setCover(cover); //来自于description描述的第一幅图
            movieService.addMovie(movie);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Map getMovie(@PathVariable("id") Long id){
        Map res = new HashMap();
        try {
            Movie movie = movieService.getMovie(id);
            res.put("code", "0");
            res.put("msg", "success");
            res.put("data", movie);
        } catch (BussinessException ex){
            ex.printStackTrace();
            res.put("code", "F1");
            res.put("msg", "fail to find Movie");
        }
        return res;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateMovie( Movie book){
        Movie movie = book;
        Map res = new HashMap<>();
        try {
            Movie rawMovie = movieService.getMovie(movie.getBookId());
            rawMovie.setAuthor(movie.getAuthor());
            rawMovie.setBookName(movie.getBookName());
            rawMovie.setSubTitle(movie.getSubTitle());
            rawMovie.setDescription(movie.getDescription());
            rawMovie.setCategoryId(movie.getCategoryId());
            Document doc = Jsoup.parse(movie.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawMovie.setCover(cover);
            movieService.updateMovie(rawMovie);
            res.put("code", "0");
            res.put("msg", "success update!");
        }catch (BussinessException ex){
            ex.printStackTrace();
            res.put("code", "F2");
            res.put("msg", "fail to update Movie");
        }

        return res;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteMovie(@PathVariable("id") Long id){
        Map res = new HashMap<>();
        try{
            movieService.deleteMovie(id);
            res.put("code", "0");
            res.put("msg", "success delete!");
        }catch (BussinessException ex){
            ex.printStackTrace();
            res.put("code", "F3");
            res.put("msg", "fail to delete Movie");
        }
        return res;
    }




}
