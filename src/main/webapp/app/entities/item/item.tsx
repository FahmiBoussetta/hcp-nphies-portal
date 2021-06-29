import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './item.reducer';
import { IItem } from 'app/shared/model/item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Item = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const itemList = useAppSelector(state => state.item.entities);
  const loading = useAppSelector(state => state.item.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="item-heading" data-cy="ItemHeading">
        <Translate contentKey="hcpNphiesPortalApp.item.home.title">Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.item.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.item.home.createLabel">Create new Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {itemList && itemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.sequence">Sequence</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.isPackage">Is Package</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.tax">Tax</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.payerShare">Payer Share</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.patientShare">Patient Share</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.careTeamSequence">Care Team Sequence</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.transportationSRCA">Transportation SRCA</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.imaging">Imaging</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.laboratory">Laboratory</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.medicalDevice">Medical Device</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.oralHealthIP">Oral Health IP</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.oralHealthOP">Oral Health OP</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.procedure">Procedure</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.services">Services</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.medicationCode">Medication Code</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.servicedDate">Serviced Date</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.servicedDateStart">Serviced Date Start</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.servicedDateEnd">Serviced Date End</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.unitPrice">Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.factor">Factor</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.bodySite">Body Site</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.subSite">Sub Site</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.item.claim">Claim</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {itemList.map((item, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${item.id}`} color="link" size="sm">
                      {item.id}
                    </Button>
                  </td>
                  <td>{item.sequence}</td>
                  <td>{item.isPackage ? 'true' : 'false'}</td>
                  <td>{item.tax}</td>
                  <td>{item.payerShare}</td>
                  <td>{item.patientShare}</td>
                  <td>{item.careTeamSequence}</td>
                  <td>{item.transportationSRCA}</td>
                  <td>{item.imaging}</td>
                  <td>{item.laboratory}</td>
                  <td>{item.medicalDevice}</td>
                  <td>{item.oralHealthIP}</td>
                  <td>{item.oralHealthOP}</td>
                  <td>{item.procedure}</td>
                  <td>{item.services}</td>
                  <td>{item.medicationCode}</td>
                  <td>{item.servicedDate ? <TextFormat type="date" value={item.servicedDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {item.servicedDateStart ? <TextFormat type="date" value={item.servicedDateStart} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{item.servicedDateEnd ? <TextFormat type="date" value={item.servicedDateEnd} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{item.quantity}</td>
                  <td>{item.unitPrice}</td>
                  <td>{item.factor}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.BodySiteEnum.${item.bodySite}`} />
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.SubSiteEnum.${item.subSite}`} />
                  </td>
                  <td>{item.claim ? <Link to={`claim/${item.claim.id}`}>{item.claim.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${item.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${item.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${item.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.item.home.notFound">No Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Item;
