import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CareTeam from './care-team';
import CareTeamDetail from './care-team-detail';
import CareTeamUpdate from './care-team-update';
import CareTeamDeleteDialog from './care-team-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CareTeamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CareTeamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CareTeamDetail} />
      <ErrorBoundaryRoute path={match.url} component={CareTeam} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CareTeamDeleteDialog} />
  </>
);

export default Routes;
