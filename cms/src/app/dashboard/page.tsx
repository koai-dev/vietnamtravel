import { AnalyticsCard } from "@/components/dashboard/AnalyticsCard";
import { NewUsersList } from "@/components/dashboard/NewUsersList";
import { TrackingChart } from "@/components/dashboard/TrackingChart";
import { Users, LineChart } from "lucide-react";

export default function DashboardPage() {
  return (
    <div className="space-y-4">
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <AnalyticsCard
          title="Today's Visits"
          value="1,234"
          icon={<LineChart className="h-4 w-4 text-muted-foreground" />}
        />
        <AnalyticsCard
          title="This Month's Visits"
          value="45,678"
          icon={<LineChart className="h-4 w-4 text-muted-foreground" />}
        />
        <AnalyticsCard
          title="New Users Today"
          value="5"
          icon={<Users className="h-4 w-4 text-muted-foreground" />}
        />
        <AnalyticsCard
          title="New Users This Month"
          value="123"
          icon={<Users className="h-4 w-4 text-muted-foreground" />}
        />
      </div>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <div className="col-span-4">
          <TrackingChart />
        </div>
        <div className="col-span-3">
          <NewUsersList />
        </div>
      </div>
    </div>
  );
}
