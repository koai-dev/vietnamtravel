import { ModeToggle } from "./mode-toggle";

export function Header() {
  return (
    <header className="flex h-16 items-center justify-between border-b px-4">
      <div></div>
      <div className="flex items-center gap-4">
        <ModeToggle />
        <div>User Dropdown</div>
      </div>
    </header>
  );
}
