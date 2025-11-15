export function Sidebar() {
  return (
    <aside className="w-64 border-r">
      <div className="p-4">
        <h2 className="text-2xl font-bold">CMS</h2>
      </div>
      <nav className="flex flex-col p-4">
        <a href="/dashboard" className="rounded-lg p-2 hover:bg-gray-100">Dashboard</a>
        <a href="/dashboard/provinces" className="rounded-lg p-2 hover:bg-gray-100">Provinces</a>
        <a href="/dashboard/attractions" className="rounded-lg p-2 hover:bg-gray-100">Attractions</a>
        <a href="/dashboard/foods" className="rounded-lg p-2 hover:bg-gray-100">Local Food</a>
        <a href="/dashboard/restaurants" className="rounded-lg p-2 hover:bg-gray-100">Restaurants</a>
        <a href="/dashboard/hotels" className="rounded-lg p-2 hover:bg-gray-100">Hotels</a>
        <a href="/dashboard/seasonal" className="rounded-lg p-2 hover:bg-gray-100">Seasonal Hot Places</a>
        <a href="/dashboard/users" className="rounded-lg p-2 hover:bg-gray-100">Users</a>
      </nav>
    </aside>
  );
}
