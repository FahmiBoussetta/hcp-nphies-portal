import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Encounter from './encounter';
import EncounterDetail from './encounter-detail';
import EncounterUpdate from './encounter-update';
import EncounterDeleteDialog from './encounter-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EncounterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EncounterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EncounterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Encounter} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EncounterDeleteDialog} />
  </>
);

export default Routes;
