export function Sidebar() {
  return (
    <aside className="w-64 border-r">
      <div className="p-4">
        <h2 className="text-2xl font-bold">CMS</h2>
      </div>
      <nav className="flex flex-col p-4">
        <a href="/" className="rounded-lg p-2 hover:bg-gray-100">Dashboard</a>
        <a href="/provinces" className="rounded-lg p-2 hover:bg-gray-100">Provinces</a>
        <a href="/attractions" className="rounded-lg p-2 hover:bg-gray-100">Attractions</a>
        <a href="/foods" className="rounded-lg p-2 hover:bg-gray-100">Local Food</a>
        <a href="/restaurants" className="rounded-lg p-2 hover:bg-gray-100">Restaurants</a>
        <a href="/hotels" className="rounded-lg p-2 hover:bg-gray-100">Hotels</a>
        <a href="/seasonal" className="rounded-lg p-2 hover:bg-gray-100">Seasonal Hot Places</a>
        <a href="/users" className="rounded-lg p-2 hover:bg-gray-100">Users</a>
      </nav>
    </aside>
  );
}
