package com.ads.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class hashtagcounter {
	public static void main(String[] args) throws FileNotFoundException  {

		HashMap<String, Node> mainMap = new HashMap<String, Node>();

		int argCount = args.length;

		if (argCount < 1) {
			System.out.println("Exiting as input file is not specified as argument...");
			System.exit(0);

		}

		String input_file = args[0];
		File file_input = new File(input_file);
		Scanner read = new Scanner(file_input);
		FibonacciHeap fibheap = new FibonacciHeap();

		if (argCount == 1) {

			try {
				while (read.hasNextLine()) {
					String str = read.nextLine();

					String[] line_detail = str.split(" ");

					if (line_detail[0].startsWith("#")) {

						String key = line_detail[0].substring(1);
						int data = Integer.parseInt(line_detail[1]);

						if (mainMap.containsKey(key)) {
							fibheap.increaseKey(data, mainMap.get(key));
						} else {
							Node node = fibheap.insertKey(data, key);
							mainMap.put(key, node);
						}

					}

					else if (line_detail[0].equalsIgnoreCase("stop")) {
						if (read != null) {
							read.close();
						}

						break;

					} else {

						String outputStr = "";
						int query_no = Integer.parseInt(line_detail[0]);

						if (query_no > 0) {
							List<String> PopularTag = fibheap.extractMax(query_no);

							for (String res : PopularTag) {
								outputStr += res + ",";
							}

							System.out.print(outputStr.substring(0, outputStr.length() - 1));
							System.out.println();
						}
					}

				}

				if (read != null) {
					read.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Unknown exception...");
			}

		}
		if (argCount == 2) {

			try {
				String output_file = args[1];
				File file_output = new File(output_file);
				FileWriter fw = new FileWriter(file_output);
				PrintWriter write = new PrintWriter(fw);

				while (read.hasNextLine()) {
					String str = read.nextLine();

					String[] line_detail = str.split(" ");

					if (line_detail[0].startsWith("#")) {

						String key = line_detail[0].substring(1);
						int data = Integer.parseInt(line_detail[1]);

						if (mainMap.containsKey(key)) {
							fibheap.increaseKey(data, mainMap.get(key));
						} else {
							Node node = fibheap.insertKey(data, key);
							mainMap.put(key, node);
						}

					}

					else if (line_detail[0].equalsIgnoreCase("stop")) {
						if (read != null) {
							read.close();
						}

						if (write != null) {
							write.close();
						}

						break;

					} else {

						String outputStr = "";
						int query_no = Integer.parseInt(line_detail[0]);

						if (query_no > 0) {
							List<String> PopularTag = fibheap.extractMax(query_no);

							for (String res : PopularTag) {
								outputStr += res + ",";
							}

							write.print(outputStr.substring(0, outputStr.length() - 1));
							write.println();
						}
					}

				}

				if (read != null) {
					read.close();
				}

				if (write != null) {
					write.close();
				}

			}

			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Unknown exception...");
			}
		}
	}
}
