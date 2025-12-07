const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/04";

const Pos = struct {
	x: usize,
	y: usize
};

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

	for(grid, 0..) |row, row_index| {
		for(row, 0..) |pos, column_index| {
			if(pos == '@') {
				var adjacents: u16 = 0;
				if(row_index >= 1			and column_index >= 1)			{ adjacents += grid[row_index-1][column_index-1]; } else { adjacents += '.'; }
				if(row_index >= 1			and column_index >= 0)			{ adjacents += grid[row_index-1][column_index];	} 	else { adjacents += '.'; }
				if(row_index >= 1			and column_index < row.len-1)	{ adjacents += grid[row_index-1][column_index+1]; } else { adjacents += '.'; }
				if(row_index >= 0			and column_index >= 1)			{ adjacents += grid[row_index][column_index-1];	}	else { adjacents += '.'; }
				if(row_index >= 0			and column_index < row.len-1)	{ adjacents += grid[row_index][column_index+1]; } 	else { adjacents += '.'; }
				if(row_index < grid.len-1	and column_index >= 1)			{ adjacents += grid[row_index+1][column_index-1]; } else { adjacents += '.'; }
				if(row_index < grid.len-1	and column_index >= 0)			{ adjacents += grid[row_index+1][column_index]; } 	else { adjacents += '.'; }
				if(row_index < grid.len-1	and column_index < row.len-1)	{ adjacents += grid[row_index+1][column_index+1]; } else { adjacents += '.'; }

				if(adjacents <= 422) {
					output += 1;
				}
			}
		}
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
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

	var just_removed: u16 = 9999;
	var removeables: std.ArrayList(Pos) = .empty;
	while(just_removed > 0) {
		just_removed = 0;
		for(grid, 0..) |row, row_index| {
			for(row, 0..) |pos, column_index| {
				if(pos == '@') {
					var adjacents: u16 = 0;
					if(row_index >= 1			and column_index >= 1)			{ adjacents += grid[row_index-1][column_index-1]; } else { adjacents += '.'; }
					if(row_index >= 1			and column_index >= 0)			{ adjacents += grid[row_index-1][column_index];	} 	else { adjacents += '.'; }
					if(row_index >= 1			and column_index < row.len-1)	{ adjacents += grid[row_index-1][column_index+1]; } else { adjacents += '.'; }
					if(row_index >= 0			and column_index >= 1)			{ adjacents += grid[row_index][column_index-1];	}	else { adjacents += '.'; }
					if(row_index >= 0			and column_index < row.len-1)	{ adjacents += grid[row_index][column_index+1]; } 	else { adjacents += '.'; }
					if(row_index < grid.len-1	and column_index >= 1)			{ adjacents += grid[row_index+1][column_index-1]; } else { adjacents += '.'; }
					if(row_index < grid.len-1	and column_index >= 0)			{ adjacents += grid[row_index+1][column_index]; } 	else { adjacents += '.'; }
					if(row_index < grid.len-1	and column_index < row.len-1)	{ adjacents += grid[row_index+1][column_index+1]; } else { adjacents += '.'; }

					if(adjacents <= 422) {
						removeables.append(allocator, Pos{.x = row_index, .y = column_index}) catch {
							std.debug.print("failed appending to arraylist", .{}); };
					}
				}
			}
		}
		const removeablesSlice = removeables.toOwnedSlice(allocator) catch {
			std.debug.print("failed arraylist to slice", .{});
			return 0; };
		for(removeablesSlice) |pos| {
			grid[pos.x][pos.y] = '.';
			just_removed += 1;
			output += 1;
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
