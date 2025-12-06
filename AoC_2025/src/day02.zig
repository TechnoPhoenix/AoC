const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/02";

const Range = struct {
	min: u64,
	max: u64
};

fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var ranges: std.ArrayList(Range) = .empty;
	var min: u64 = 0;
	var max: u64 = 0;
	var toggle: bool = false;
	for(input) |char| {
		if(char == ',') {
			//between ranges
			const r: Range = Range{.min = min, .max = max};
			ranges.append(allocator, r) catch
				{ std.debug.print("failed appending range", .{}); };
			toggle = false;
			min = 0;
			max = 0;
		} else if (char == '-') {
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
	const r: Range = Range{.min = min, .max = max};
	ranges.append(allocator, r) catch
		{ std.debug.print("failed appending range", .{}); };

	const rangesSlice = ranges.toOwnedSlice(allocator) catch {
		std.debug.print("failed appending range", .{});
		return 0; };
	defer allocator.free(rangesSlice);
	for(rangesSlice) |range| {
		for(range.min..range.max) |num| {
			const length: u64 = @intFromFloat(@ceil(@log10(@as(f64, @floatFromInt(num)))));
			if(@mod(length, 2) == 0) {
				const half_length_num = std.math.pow(u64, 10, length/2);
				const first_half = @divTrunc(num, half_length_num);
				const second_half = @mod(num, half_length_num);
				if(first_half == second_half) {
					output += num;
				}
			}
		}
	}
	return output;
}

pub fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var ranges: std.ArrayList(Range) = .empty;
	var min: u64 = 0;
	var max: u64 = 0;
	var toggle: bool = false;
	for(input) |char| {
		if(char == ',') {
			//between ranges
			const r: Range = Range{.min = min, .max = max};
			ranges.append(allocator, r) catch
				{ std.debug.print("failed appending range", .{}); };
			toggle = false;
			min = 0;
			max = 0;
		} else if (char == '-') {
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
	const r: Range = Range{.min = min, .max = max};
	ranges.append(allocator, r) catch
		{ std.debug.print("failed appending range", .{}); };

		const rangesSlice = ranges.toOwnedSlice(allocator) catch {
		std.debug.print("failed appending range", .{});
		return 0; };
		defer allocator.free(rangesSlice);
	for(rangesSlice) |range| {
		for(range.min..range.max) |num| {
			if(num >= 10) {
				var length: u64 = @intFromFloat(@ceil(@log10(@as(f64, @floatFromInt(num)))));
				if(@mod(@log10(@as(f64, @floatFromInt(num))), 1.0) == 0.0) {
					length += 1;
				}
				for(1..(length)) |div| {
					if(@mod(length, div) == 0) {
						const numStr = std.fmt.allocPrint(allocator, "{}", .{num}) catch "";
						defer allocator.free(numStr);
						const pattern = numStr[0..div];
						std.debug.assert(length == numStr.len);
						var repeats: bool = true;
						for(0..length) |index| {
							if(numStr[index] != pattern[@mod(index,pattern.len)]) {
								repeats = false;
							}
						}
						if(repeats) {
							output += num;
							break;
						}
					}
				}
			}
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
