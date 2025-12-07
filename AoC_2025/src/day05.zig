const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/05";

const Range = struct {
	min: u64,
	max: u64
};

fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var split: usize = 0;
	for(0..input.len-1) |index| {
		if(input[index] == '\n' and input[index+1] == '\n') {
			split = index;
		}
	}
	const range_input = input[0..split];
	const id_input = input[split+2..input.len];
	var ranges: std.ArrayList(Range) = .empty;
	var ids: std.ArrayList(u64) = .empty;
	var min: u64 = 0;
	var max: u64 = 0;
	var toggle: bool = false;
	for(range_input) |char| {
		if(char == '\n') {
			ranges.append(allocator, Range{.min = min, .max = max}) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			min = 0;
			max = 0;
			toggle = false;
		} else if(char == '-') {
			toggle = true;
		} else {
			if(toggle) {
				max *= 10;
				max += char-48;
			}
			if(!toggle) {
				min *= 10;
				min += char-48;
			}
		}
	}
	ranges.append(allocator, Range{.min = min, .max = max}) catch {
		std.debug.print("failed appending to arraylist", .{}); };

	var id: u64 = 0;
	for(id_input) |char| {
		if(char == '\n') {
			ids.append(allocator, id) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			id = 0;
		} else {
			id *= 10;
			id += char-48;
		}
	}
	ids.append(allocator, id) catch {
		std.debug.print("failed appending to arraylist", .{}); };

	const idsSlice = ids.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	const rangesSlice = ranges.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	for(idsSlice) |cid| {
		for(rangesSlice) |range| {
			if(range.min <= cid and cid <= range.max) {
				output += 1;
				break;
			}
		}
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var split: usize = 0;
	for(0..input.len-1) |index| {
		if(input[index] == '\n' and input[index+1] == '\n') {
			split = index;
		}
	}
	const range_input = input[0..split];
	var ranges: std.ArrayList(Range) = .empty;
	var min: u64 = 0;
	var max: u64 = 0;
	var toggle: bool = false;
	for(range_input) |char| {
		if(char == '\n') {
			ranges.append(allocator, Range{.min = min, .max = max}) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			min = 0;
			max = 0;
			toggle = false;
		} else if(char == '-') {
			toggle = true;
		} else {
			if(toggle) {
				max *= 10;
				max += char-48;
			}
			if(!toggle) {
				min *= 10;
				min += char-48;
			}
		}
	}
	ranges.append(allocator, Range{.min = min, .max = max}) catch {
		std.debug.print("failed appending to arraylist", .{}); };

	const rangesSlice = ranges.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	var sorted: std.ArrayList(Range) = .empty;
	var x: u64 = 0;
	const deadRange = Range{.min = std.math.maxInt(u64), .max = std.math.maxInt(u64)};
	while(x < rangesSlice.len) : (x += 1)  {
		var smallest: u64 = std.math.maxInt(u64);
		var smallInd: u64 = std.math.maxInt(u64);
		for(0..rangesSlice.len) |index| {
			if(rangesSlice[index].min < smallest) {
				smallest = rangesSlice[index].min;
				smallInd = index;
			}
		}
		sorted.append(allocator, Range{.min = rangesSlice[smallInd].min, .max = rangesSlice[smallInd].max}) catch {
			std.debug.print("failed appending to arraylist", .{}); };
		rangesSlice[smallInd] = deadRange;
	}

	const sortedSlice = sorted.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	output += sortedSlice[0].max - sortedSlice[0].min + 1;
	var prev: u64 = sortedSlice[0].max + 1;
	for(1..sortedSlice.len) |index| {
		if(sortedSlice[index].max < prev) {
			continue;
		}
		output += sortedSlice[index].max - @max(prev, sortedSlice[index].min) + 1;
		prev = sortedSlice[index].max+1;
	}

	return output;
}

pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
