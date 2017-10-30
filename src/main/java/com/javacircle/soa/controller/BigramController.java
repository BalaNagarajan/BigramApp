package com.javacircle.soa.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javacircle.soa.service.BigramProcessorService;

/**
 * The purpose of this Controller is to parse the input file and display the
 * bigrams with the count
 * 
 * @author Bala
 *
 */

@Controller
public class BigramController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigramController.class);
	@Autowired
	private BigramProcessorService processorService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String processFile(@RequestParam("file") MultipartFile multipart, Model model) throws IOException {
		LOGGER.info("Start::POST::processFile()");
		BufferedReader br;
		List<String> result = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		InputStreamReader reader = null;
		InputStream is = null;
		String processed = null;
		try {
			String line;
			is = multipart.getInputStream();
			reader = new InputStreamReader(is);
			br = new BufferedReader(reader);
			while ((line = br.readLine()) != null) {
				result.add(line);
				builder.append(line);
				builder.append(" ");
			}
			processed = processorService.process(builder.toString());

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			reader.close();
			is.close();
		}

		model.addAttribute("result", processed);
		LOGGER.info("End::POST::processFile()");
		return "result";

	}

}
