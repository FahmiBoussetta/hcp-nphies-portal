import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Related from './related';
import RelatedDetail from './related-detail';
import RelatedUpdate from './related-update';
import RelatedDeleteDialog from './related-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RelatedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RelatedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RelatedDetail} />
      <ErrorBoundaryRoute path={match.url} component={Related} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RelatedDeleteDialog} />
  </>
);

export default Routes;
