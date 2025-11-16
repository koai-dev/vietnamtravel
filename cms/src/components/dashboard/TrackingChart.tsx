"use client";

import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useTrackingSummary } from "@/hooks/useDashboard";
import { useState } from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { ErrorState } from "./ErrorState";

export function TrackingChart() {
  const [range, setRange] = useState("day");
  const { data, isLoading, isError } = useTrackingSummary(range);

  if (isError) {
    return <ErrorState message="Failed to load tracking data." />;
  }

  return (
    <div>
      <Select onValueChange={setRange} defaultValue={range}>
        <SelectTrigger className="w-[180px]">
          <SelectValue placeholder="Filter by range" />
        </SelectTrigger>
        <SelectContent>
          <SelectItem value="hour">Hour</SelectItem>
          <SelectItem value="day">Day</SelectItem>
          <SelectItem value="week">Week</SelectItem>
          <SelectItem value="month">Month</SelectItem>
          <SelectItem value="year">Year</SelectItem>
        </SelectContent>
      </Select>

      {isLoading ? (
        <Skeleton className="w-full h-[350px]" />
      ) : (
        <ResponsiveContainer width="100%" height={350}>
          <BarChart data={data?.data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="timestamp" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="visits" fill="#8884d8" />
          </BarChart>
        </ResponsiveContainer>
      )}
    </div>
  );
}
