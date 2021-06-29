import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './exemption-component.reducer';
import { IExemptionComponent } from 'app/shared/model/exemption-component.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ExemptionComponent = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const exemptionComponentList = useAppSelector(state => state.exemptionComponent.entities);
  const loading = useAppSelector(state => state.exemptionComponent.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="exemption-component-heading" data-cy="ExemptionComponentHeading">
        <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.home.title">Exemption Components</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.home.createLabel">Create new Exemption Component</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {exemptionComponentList && exemptionComponentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.start">Start</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.end">End</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.costToBeneficiary">Cost To Beneficiary</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {exemptionComponentList.map((exemptionComponent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${exemptionComponent.id}`} color="link" size="sm">
                      {exemptionComponent.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.ExemptionTypeEnum.${exemptionComponent.type}`} />
                  </td>
                  <td>
                    {exemptionComponent.start ? <TextFormat type="date" value={exemptionComponent.start} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {exemptionComponent.end ? <TextFormat type="date" value={exemptionComponent.end} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {exemptionComponent.costToBeneficiary ? (
                      <Link to={`cost-to-beneficiary-component/${exemptionComponent.costToBeneficiary.id}`}>
                        {exemptionComponent.costToBeneficiary.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${exemptionComponent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${exemptionComponent.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${exemptionComponent.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.home.notFound">No Exemption Components found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ExemptionComponent;
