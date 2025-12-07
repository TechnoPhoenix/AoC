const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/03";

fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var banks: std.ArrayList([]u8) = .empty;
	var bank: std.ArrayList(u8) = .empty;
	for(input) |char| {
		if(char == '\n') {
			const temp = bank.toOwnedSlice(allocator) catch {
				std.debug.print("failed arraylist to slice", .{});
				return 0; };
			banks.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else {
			bank.append(allocator, (char-48)) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}
	const temp = bank.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	banks.append(allocator, temp) catch {
		std.debug.print("failed appending to arraylist", .{}); };


	const banksSlice = banks.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	for(banksSlice) |current| {
		var highestIndex: u8 = 0;
		var highest1: u8 = 0;
		for(0..(current.len-1)) |index| {
			if(current[index] > highest1) {
				highestIndex = @intCast(index);
				highest1 = current[index];
			}
		}
		var highest2:u8 = 0;
		for((highestIndex+1)..current.len) |index| {
			if(current[index] > highest2) {
				highest2 = current[index];
			}
		}
		output += (highest1*10) + highest2;
	}
	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var banks: std.ArrayList([]u8) = .empty;
	var bank: std.ArrayList(u8) = .empty;
	for(input) |char| {
		if(char == '\n') {
			const temp = bank.toOwnedSlice(allocator) catch {
				std.debug.print("failed arraylist to slice", .{});
				return 0; };
			banks.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else {
			bank.append(allocator, (char-48)) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}
	const temp = bank.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	banks.append(allocator, temp) catch {
		std.debug.print("failed appending to arraylist", .{}); };


	const banksSlice = banks.toOwnedSlice(allocator) catch {
		std.debug.print("failed arraylist to slice", .{});
		return 0; };
	for(banksSlice) |current| {
		var currentJoltage: u64 = 0;
		var startIndex: u8 = 0;
		var remainingDigits: u8 = 12;
		while(remainingDigits > 0) : (remainingDigits -= 1) {
			var currentSearch: u8 = 9;
			outer: while(true) {
				for(startIndex..(current.len-remainingDigits+1)) |index| {
					if(current[index] == currentSearch) {
						currentJoltage *= 10;
						currentJoltage += current[index];
						startIndex = @intCast(index + 1);
						break :outer;
					}
				}
				currentSearch -= 1;
			}
		}
		output += currentJoltage;
	}
	return output;
}


pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
