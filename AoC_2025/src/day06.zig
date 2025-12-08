const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/06";

const Problem = struct{
	operands: std.ArrayList(u64),
	operator: u8
};

fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var problems: std.AutoHashMap(u64, Problem) = .init(allocator);
	var lines = std.mem.splitAny(u8, input, "\n");
	while(lines.next()) |line_bad| {
		const line = std.mem.trim(u8, line_bad, " ");
		var key_counter: u64 = 0;
		if('0' <= line[0] and line[0] <= '9') {
			var numbers = std.mem.splitAny(u8, line, " ");
			while(numbers.next()) |num| {
				if(num.len > 0) {
					var number: u64 = 0;
					for(num) |char| {
						number *= 10;
						number += char-48;
					}
					if(problems.contains(key_counter)) {
						var temp = problems.getPtr(key_counter) orelse unreachable;
						temp.operands.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
					} else {
						var temp = Problem{.operands = .empty, .operator = ' '};
						temp.operands.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						problems.put(key_counter, temp) catch {
							std.debug.print("failed putting in hashmap", .{}); };
					}
					key_counter += 1;
				}
			}
		} else {
			var operators = std.mem.splitAny(u8, line, " ");
			while(operators.next()) |operator| {
				if(operator.len > 0) {
					var temp = problems.getPtr(key_counter) orelse unreachable;
					temp.operator = operator[0];
					key_counter += 1;
				}
			}
		}
	}
	var iter = problems.iterator();
	while(iter.next()) |problem_entry| {
		const problem = problem_entry.value_ptr;
		if(problem.operator == '+') {
			var temp: u64 = 0;
			for(problem.operands.items) |operand| {
				temp += operand;
			}
			output += temp;
		} else if (problem.operator == '*') {
			var temp: u64 = 1;
			for(problem.operands.items) |operand| {
				temp *= operand;
			}
			output += temp;
		} else {
			unreachable;
		}
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var lines = std.mem.splitSequence(u8, input, "\n");
	const first_line = lines.next() orelse unreachable;
	var columnsList: std.ArrayList(std.ArrayList(u8)) = .empty;
	for(first_line) |char| {
		if(char != 'e') {
			columnsList.append(allocator, .empty) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}
	var columns = columnsList.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	for(first_line, 0..) |char, index| {
		columns[index].append(allocator, char) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}
	while(lines.next()) |line| {
		for(line, 0..) |char, index| {
			columns[index].append(allocator, char) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}

	var problems: std.ArrayList(Problem) = .empty;
	var problem = Problem{.operands = .empty, .operator = ' '};
	for(columns) |column| {
		var toggle: bool = true;
		var number: u64 = 0;
		for(column.items) |char| {
			if(char == ' ') {
				continue;
			} else if(char == '*' or  char == '+') {
				problem.operator = char;
				toggle = false;
			} else {
				number *= 10;
				number += char-48;
				toggle = false;
			}
		}
		if(toggle) {
			problems.append(allocator, problem) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			problem = Problem{.operands = .empty, .operator = ' '};
		}
		if(number > 0) {
			problem.operands.append(allocator, number) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}
	problems.append(allocator, problem) catch {
		std.debug.print("failed appending to arraylist", .{}); };

	for(problems.items) |prob| {
		if(prob.operator == '+') {
			var temp: u64 = 0;
			for(prob.operands.items) |operand| {
				temp += operand;
			}
			output += temp;
		} else if(prob.operator == '*') {
			var temp: u64 = 1;
			for(prob.operands.items) |operand| {
				temp *= operand;
			}
			output += temp;
		} else {
			unreachable;
		}
	}

	return output;
}

pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
