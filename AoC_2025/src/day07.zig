const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/07";

fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var columns: std.ArrayList([]u8) = .empty;
	var line: std.ArrayList(u8) = .empty;
	for(input) |char| {
		if(char == '\n') {
			const temp = line.toOwnedSlice(allocator) catch {
				std.debug.print("failed arraylist to slice", .{});
				return 0; };
			columns.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else {
			line.append(allocator, char) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}
	const temp = line.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	columns.append(allocator, temp) catch {
		std.debug.print("failed appending to arraylist", .{}); };

	const grid: [][]u8 = columns.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };

	for(grid[0], 0..) |el, index| {
		if(el == 'S') {
			grid[0][index] = '|';
		}
	}
	for(1..grid.len) |i| {
		for(0..grid[i].len) |j| {
			if(grid[i][j] == '.') {
				if(grid[i-1][j] == '|') {
					grid[i][j] = '|';
				}
			} else if(grid[i][j] == '^') {
				if(grid[i-1][j] == '|') {
					if(j > 0) {
						grid[i][j-1] = '|';
					}
					if(j < grid[i].len) {
						grid[i][j+1] = '|';
					}
					output += 1;
				}
			}
		}
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var columns: std.ArrayList([]u64) = .empty;
	var line: std.ArrayList(u64) = .empty;
	for(input) |char| {
		if(char == '\n') {
			const temp = line.toOwnedSlice(allocator) catch {
				std.debug.print("failed arraylist to slice", .{});
				return 0; };
			columns.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else {
			line.append(allocator, char) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}
	const temp = line.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	columns.append(allocator, temp) catch {
		std.debug.print("failed appending to arraylist", .{}); };

	const grid: [][]u64 = columns.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };

	for(grid[0], 0..) |el, index| {
		if(el == 'S') {
			grid[0][index] = '|';
		}
	}
	for(1..grid.len) |i| {
		for(0..grid[i].len) |j| {
			if(grid[i][j] == '.') {
				if(grid[i-1][j] == '|') {
					grid[i][j] = '|';
				}
			} else if(grid[i][j] == '^') {
				if(grid[i-1][j] == '|') {
					if(j > 0) {
						grid[i][j-1] = '|';
					}
					if(j < grid[i].len) {
						grid[i][j+1] = '|';
					}
				}
			}
		}
	}

	for(0..grid[grid.len-1].len) |index| {
		if(grid[grid.len-1][index] == '|') {
			grid[grid.len-1][index] = 1;
		}
	}
	var i: u64 = grid.len - 1;
	while(i > 0) : (i -= 1) {
		for(0..grid[i].len) |j| {
			if(grid[i][j] == '^') {
				grid[i-1][j] = grid[i][j-1] + grid[i][j+1];
			}
			if(grid[i-1][j] == '|') {
				grid[i-1][j] = grid[i][j];
			}
		}
	}
	for(0..grid[0].len) |index| {
		if(grid[0][index] != '.') {
			output = grid[0][index];
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
