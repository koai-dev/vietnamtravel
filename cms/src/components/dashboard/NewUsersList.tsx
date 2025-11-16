"use client";

import { useNewUsers } from "@/hooks/useDashboard";
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Skeleton } from "@/components/ui/skeleton";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { ErrorState } from "./ErrorState";

export function NewUsersList() {
  const [offset, setOffset] = useState(0);
  const limit = 20;
  const { data, isLoading, isError } = useNewUsers(limit, offset);

  if (isError) {
    return <ErrorState message="Failed to load new users." />;
  }

  const handleNextPage = () => {
    if (data && data.total > offset + limit) {
      setOffset(offset + limit);
    }
  };

  const handlePrevPage = () => {
    if (offset > 0) {
      setOffset(offset - limit);
    }
  };

  return (
    <div>
      {isLoading ? (
        <div className="space-y-2">
          <Skeleton className="h-8 w-full" />
          <Skeleton className="h-8 w-full" />
          <Skeleton className="h-8 w-full" />
          <Skeleton className="h-8 w-full" />
          <Skeleton className="h-8 w-full" />
        </div>
      ) : (
        <>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Name</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Created At</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {data?.items.map((user) => (
                <TableRow key={user.id}>
                  <TableCell>{user.name}</TableCell>
                  <TableCell>{user.email}</TableCell>
                  <TableCell>{new Date(user.createdAt).toLocaleDateString()}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          <div className="flex justify-end space-x-2 mt-4">
            <Button onClick={handlePrevPage} disabled={offset === 0}>
              Previous
            </Button>
            <Button onClick={handleNextPage} disabled={!data || data.total <= offset + limit}>
              Next
            </Button>
          </div>
        </>
      )}
    </div>
  );
}
