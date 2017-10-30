package com.javacircle.soa.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Service class to compute the count of Bigram
 * 
 * @author Bala
 *
 */
@Service
public class BigramProcessorService {

	public String process(String line) {

		StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(line)) {
			line = line.replaceAll("[-+.^:,*@$#'/?]","");
			String[] lineArr = line.split(" ");
			String firstWord;
			String keyWord;
			Map<String, Integer> keyMap = new LinkedHashMap<String, Integer>();
			int count = 1;

			if (lineArr != null && lineArr.length > 0) {
				for (int wordCounter = 0; wordCounter <= lineArr.length; wordCounter++) {
					if (wordCounter <= lineArr.length - 2) {
						firstWord = lineArr[wordCounter].trim().toLowerCase();
						keyWord = lineArr[wordCounter + 1].trim().toLowerCase();
						for (int checker = 0; checker < lineArr.length; checker++) {
							if (checker < lineArr.length - 1) {
								if (firstWord.equalsIgnoreCase(lineArr[checker])
										&& keyWord.equalsIgnoreCase(lineArr[checker + 1])) {
									count++;
								}
							}
						}
						if (!keyMap.containsKey(firstWord + "," + keyWord)) {
							keyMap.put(firstWord + "," + keyWord, count - 1);
							count = 1;
						} else {
							count = 1;
						}

					}

				}

				if (keyMap != null) {
					Set<String> keySet = keyMap.keySet();
					for (String s : keySet) {
						System.out.println(s + ":" + keyMap.get(s));
						builder.append(s + ":" + keyMap.get(s));
						builder.append(" | ");

					}
				}
			}
		} else {

		}

		return builder.toString();

	}

}
