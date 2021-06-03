import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './hospitalization.reducer';
import { IHospitalization } from 'app/shared/model/hospitalization.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHospitalizationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Hospitalization = (props: IHospitalizationProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { hospitalizationList, match, loading } = props;
  return (
    <div>
      <h2 id="hospitalization-heading" data-cy="HospitalizationHeading">
        <Translate contentKey="hcpNphiesPortalApp.hospitalization.home.title">Hospitalizations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.hospitalization.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.hospitalization.home.createLabel">Create new Hospitalization</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {hospitalizationList && hospitalizationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.admitSource">Admit Source</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.reAdmission">Re Admission</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.dischargeDisposition">Discharge Disposition</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.origin">Origin</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {hospitalizationList.map((hospitalization, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${hospitalization.id}`} color="link" size="sm">
                      {hospitalization.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.AdmitSourceEnum.${hospitalization.admitSource}`} />
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.ReAdmissionEnum.${hospitalization.reAdmission}`} />
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.DischargeDispositionEnum.${hospitalization.dischargeDisposition}`} />
                  </td>
                  <td>
                    {hospitalization.origin ? (
                      <Link to={`organization/${hospitalization.origin.id}`}>{hospitalization.origin.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${hospitalization.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${hospitalization.id}/edit`}
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
                        to={`${match.url}/${hospitalization.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.hospitalization.home.notFound">No Hospitalizations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ hospitalization }: IRootState) => ({
  hospitalizationList: hospitalization.entities,
  loading: hospitalization.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Hospitalization);
