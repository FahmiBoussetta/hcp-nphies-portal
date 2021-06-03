import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './givens.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGivensDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GivensDetail = (props: IGivensDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { givensEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="givensDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.givens.detail.title">Givens</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{givensEntity.id}</dd>
          <dt>
            <span id="given">
              <Translate contentKey="hcpNphiesPortalApp.givens.given">Given</Translate>
            </span>
          </dt>
          <dd>{givensEntity.given}</dd>
          <dt>
            <span id="prefix">
              <Translate contentKey="hcpNphiesPortalApp.givens.prefix">Prefix</Translate>
            </span>
          </dt>
          <dd>{givensEntity.prefix}</dd>
          <dt>
            <span id="suffix">
              <Translate contentKey="hcpNphiesPortalApp.givens.suffix">Suffix</Translate>
            </span>
          </dt>
          <dd>{givensEntity.suffix}</dd>
          <dt>
            <span id="textName">
              <Translate contentKey="hcpNphiesPortalApp.givens.textName">Text Name</Translate>
            </span>
          </dt>
          <dd>{givensEntity.textName}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.givens.human">Human</Translate>
          </dt>
          <dd>{givensEntity.human ? givensEntity.human.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/givens" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/givens/${givensEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ givens }: IRootState) => ({
  givensEntity: givens.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GivensDetail);
