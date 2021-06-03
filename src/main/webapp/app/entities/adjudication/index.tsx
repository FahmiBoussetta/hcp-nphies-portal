import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Adjudication from './adjudication';
import AdjudicationDetail from './adjudication-detail';
import AdjudicationUpdate from './adjudication-update';
import AdjudicationDeleteDialog from './adjudication-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Adjudication} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationDeleteDialog} />
  </>
);

export default Routes;
