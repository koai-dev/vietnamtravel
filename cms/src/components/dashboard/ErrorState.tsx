export function ErrorState({ message }: { message: string }) {
  return (
    <div className="flex items-center justify-center h-full">
      <div className="text-red-500">{message}</div>
    </div>
  );
}
