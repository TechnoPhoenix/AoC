const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/10";

const Machine = struct{
	lights: []bool,
	buttons: [][] u64,
	joltages: [] u64
};

var cache: std.AutoArrayHashMap(u128, u64) = .init(allocator);


fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var lines = std.mem.splitAny(u8, input, "\n");
	var machines: std.ArrayList(Machine) = .empty;
	while(lines.next()) |line| {
		var pos: u64 = 0;
		var number: u64 = 0;
		var lights: std.ArrayList(bool) = .empty;
		var buttons: std.ArrayList([]u64) = .empty;
		var joltages: std.ArrayList(u64) = .empty;
		var button: std.ArrayList(u64) = .empty;
		for(line) |char| {
			switch(pos) {
				0 => {
					if(char == ']') {
						pos += 1;
					} else if(char == '[') {
					} else {
						lights.append(allocator, char == '#')  catch {
							std.debug.print("failed appending to arraylist", .{}); };
					}
				},
				1 => {
					if(char == '{') {
						pos += 1;
					} else if(char == ',') {
						button.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						number = 0;
					} else if(char == ')') {
						button.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						const temp = button.toOwnedSlice(allocator) catch {
							std.debug.print("failed arraylist to slice", .{});
							return 0; };
						buttons.append(allocator, temp) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						number = 0;
						button = .empty;
					} else if(char == '(' or char == ' ') {
					} else {
						number *= 10;
						number += char-48;
					}
				},
				2 => {
					if(char == '{') {
					} else if(char == '}') {
						joltages.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
					} else if(char == ',') {
						joltages.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						number = 0;
					} else {
						number *= 10;
						number += char-48;
					}
				},
				else => {
					unreachable;
				}
			}
		}
		const tlights = lights.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		const tbuttons = buttons.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		const tjoltages = joltages.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		const temp = Machine{.lights = tlights, .buttons = tbuttons, .joltages = tjoltages};
		machines.append(allocator, temp) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}

	for(machines.items) |machine| {
		var lowest: u64 = std.math.maxInt(u64);
		for(1..std.math.pow(u64, 2, machine.buttons.len)) |num| {
			var temp_lights_list: std.ArrayList(bool) = .empty;
			var x: u64 = 0;
			while(x < machine.lights.len) {
				temp_lights_list.append(allocator, false) catch {
					std.debug.print("failed appending to arraylist", .{}); };
				x += 1;
			}
			var temp_lights = temp_lights_list.toOwnedSlice(allocator) catch {
				std.debug.print("failed arraylist to slice", .{});
				return 0; };
			var counter: u64 = 0;
			for(machine.buttons, 0..) |button, i| {
				if(@mod(num/std.math.pow(u64, 2, i), 2) == 1) {
					for(button) |j| {
						temp_lights[j] = !temp_lights[j];
					}
					counter += 1;
				}
			}
			var check: bool = true;
			for(0..machine.lights.len) |i| {
				if(machine.lights[i] != temp_lights[i]) {
					check = false;
					break;
				}
			}
			if(check and lowest > counter) {
				lowest = counter;
			}
		}
		output += lowest;
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var lines = std.mem.splitAny(u8, input, "\n");
	var machines: std.ArrayList(Machine) = .empty;
	while(lines.next()) |line| {
		var pos: u64 = 0;
		var number: u64 = 0;
		var lights: std.ArrayList(bool) = .empty;
		var buttons: std.ArrayList([]u64) = .empty;
		var joltages: std.ArrayList(u64) = .empty;
		var button: std.ArrayList(u64) = .empty;
		for(line) |char| {
			switch(pos) {
				0 => {
					if(char == ']') {
						pos += 1;
					} else if(char == '[') {
					} else {
						lights.append(allocator, char == '#')  catch {
							std.debug.print("failed appending to arraylist", .{}); };
					}
				},
				1 => {
					if(char == '{') {
						pos += 1;
					} else if(char == ',') {
						button.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						number = 0;
					} else if(char == ')') {
						button.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						const temp = button.toOwnedSlice(allocator) catch {
							std.debug.print("failed arraylist to slice", .{});
							return 0; };
						buttons.append(allocator, temp) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						number = 0;
						button = .empty;
					} else if(char == '(' or char == ' ') {
					} else {
						number *= 10;
						number += char-48;
					}
				},
				2 => {
					if(char == '{') {
					} else if(char == '}') {
						joltages.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
					} else if(char == ',') {
						joltages.append(allocator, number) catch {
							std.debug.print("failed appending to arraylist", .{}); };
						number = 0;
					} else {
						number *= 10;
						number += char-48;
					}
				},
				else => {
					unreachable;
				}
			}
		}
		const tlights = lights.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		const tbuttons = buttons.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		const tjoltages = joltages.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		const temp = Machine{.lights = tlights, .buttons = tbuttons, .joltages = tjoltages};
		machines.append(allocator, temp) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}

	for(machines.items) |machine| {
		output += recursive_joltages(machine.joltages[0..], machine.buttons);
		cache.clearAndFree();
	}

	return output;
}

fn recursive_joltages(joltages: []u64, buttons: [][]u64) u64 {
	//test if end of recursion
	var end: u64 = 0;
	for(joltages) |joltage| {
		end += joltage;
	}
	if(end == 0) {
		return 0;
	}

	//test if all joltages are still even, then we can skip this layer
	var skip: bool = true;
	for(joltages) |joltage| {
		if(@mod(joltage, 2) == 1) {
			skip = false;
			break;
		}
	}
	if(skip) {
		var t2: std.ArrayList(u64) = .empty;
		for(joltages) |j| {
			t2.append(allocator, j/2) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
		const jol_arg = t2.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		defer allocator.free(jol_arg);
		return 2*(recursive_joltages(jol_arg, buttons));
	}

	//check cache for current joltage combination
	var jol_num: u128 = 0;
	for(joltages) |joltage| {
		jol_num *= 1000;
		jol_num += joltage;
	}
	if(cache.contains(jol_num)) {
		return cache.get(jol_num) orelse std.math.maxInt(u32);
	}

	//go trough and check all possible button combinations
	var lowest: u64 = std.math.maxInt(u32);
	o: for(1..std.math.pow(u64, 2, buttons.len)) |num| {
		var c: std.ArrayList([]u64) = .empty;
		var len: u64 = 0;
		for(buttons, 0..) |button, i| {
			if(@mod(num/std.math.pow(u64, 2, i), 2) == 1) {
				len += 1;
				c.append(allocator, button) catch {
					std.debug.print("failed appending to arraylist", .{}); };
			}
			if(len > lowest) {
				c.deinit(allocator);
				continue :o;
			}
		}
		const comb = c.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		defer allocator.free(comb);

		var t1: std.ArrayList(u64) = .empty;
		for(joltages) |j| {
			t1.append(allocator, j) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
		var t_jol = t1.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		defer allocator.free(t_jol);
		var valid: bool = true;
		for(comb) |button| {
			for(button) |i| {
				if(t_jol[i] == 0) {
					valid = false;
					break;
				}
				t_jol[i] -= 1;
			}
		}
		for(t_jol) |joltage| {
			if(@mod(joltage, 2) == 1) {
				valid = false;
				break;
			}
		}
		if(valid) {
			var t2: std.ArrayList(u64) = .empty;
			for(t_jol) |j| {
				t2.append(allocator, j/2) catch {
					std.debug.print("failed appending to arraylist", .{}); };
			}
			const jol_arg = t2.toOwnedSlice(allocator) catch {
				std.debug.print("failed arraylist to slice", .{});
				return 0; };
			var presses: u64 = comb.len;
			presses += 2*recursive_joltages(jol_arg, buttons);
			lowest = @min(lowest, presses);
			allocator.free(jol_arg);
		}
	}

	//add result to cache
	cache.put(jol_num, lowest) catch {
		std.debug.print("failed putting into hashmap", .{}); };


	//return lowest amount of button presses possible
	return lowest;
}

pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
